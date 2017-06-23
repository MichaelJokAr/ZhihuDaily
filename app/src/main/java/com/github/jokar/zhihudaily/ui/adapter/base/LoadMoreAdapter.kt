package com.github.jokar.zhihudaily.ui.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.widget.recyclerView.RecyclerOnScrollListener
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.LifecycleTransformer
import java.util.concurrent.TimeUnit


/**
 * Created by JokAr on 2017/6/21.
 */
abstract class LoadMoreAdapter<T>(var context: Context,
                                  var data: ArrayList<T>,
                                  var transformer: LifecycleTransformer<Any>)
    : RecyclerView.Adapter<BaseViewHolder>() {
    val TYPE_FOOT_VIEW: Int = 100

    var showCanLoadMore: Boolean = true

    var inflater: LayoutInflater? = null
    var recyclerView: RecyclerView? = null
    var onScrollListener: RecyclerOnScrollListener? = null
    var itemClickListener: LoadMoreAdapterItemClickListener? = null
    var mNotifyObserver: NotifyObserver? = null

    var mFootViewHolder: FootViewHolder? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView

        onScrollListener = object : RecyclerOnScrollListener(this.recyclerView!!) {
            override fun lastCompletelyVisibleItem(position: Int) {
                itemClickListener?.lastCompletelyVisibleItem(position)
            }

            override fun firstCompletelyVisibleItem(position: Int) {
                itemClickListener?.firstCompletelyVisibleItem(position)
            }

            override fun onLoadMore() {
                itemClickListener?.loadMore()
            }
        }

        this.recyclerView?.addOnScrollListener(onScrollListener)
        onScrollListener?.setCanLoad(false)
        mNotifyObserver = NotifyObserver()
        registerAdapterDataObserver(mNotifyObserver)

        if (data != null) {
            if (data.size <= 3) {
                setShowLoadMore(false)
            } else {
                setShowLoadMore(true)
            }
        }
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView?.clearOnScrollListeners()

        if (mNotifyObserver != null) {
            unregisterAdapterDataObserver(mNotifyObserver)
        }

        itemClickListener = null
        onScrollListener = null
        mNotifyObserver = null
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        if (viewType == TYPE_FOOT_VIEW) {
            mFootViewHolder = FootViewHolder(inflater?.inflate(R.layout.item_footview, parent,
                    false)!!, context)
            return mFootViewHolder!!
        } else {
            return createHolder(parent, viewType)
        }
    }

    final override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_FOOT_VIEW) {
            RxView.clicks(mFootViewHolder?.llFoot!!)
                    .compose<Any>(transformer)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe { o ->
                        if (itemClickListener != null) {
                            mFootViewHolder?.showProgress()
                            itemClickListener?.footViewClick()
                        }
                    }

        } else {
            bindView(viewHolder, position)
        }
    }

    final override fun getItemCount(): Int {

        return if (showCanLoadMore) data.size + 1 else data.size;
    }

    final override fun getItemViewType(position: Int): Int {
        if (showCanLoadMore) {
            if (position == itemCount - 1) {
                return TYPE_FOOT_VIEW
            } else {
                return getViewType(position)
            }
        }
        return getViewType(position)
    }

    abstract fun bindView(holder: BaseViewHolder, position: Int)

    abstract fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder?

    abstract fun getViewType(position: Int): Int

    fun setFootClickable(clickable: Boolean) {
        if (mFootViewHolder != null) {
            mFootViewHolder?.setClickable(clickable)
        }
    }

    /**
     * 设置是否需要显示加载更多

     * @param showLoadMore
     */
    fun setShowLoadMore(showLoadMore: Boolean) {
        showCanLoadMore = showLoadMore
        setCanLoading(showLoadMore)
    }

    /**
     * 是否需要加载更多

     * @param loading
     */
    fun setCanLoading(loading: Boolean) {
        if (onScrollListener != null) {
            onScrollListener?.setCanLoading(loading)
            onScrollListener?.setCanLoad(!loading)
        }
    }

    /**
     * 显示loading
     */
    fun showLoading() {
        try {
            if (mFootViewHolder != null) {
                mFootViewHolder?.setClickable(true)
                mFootViewHolder?.showProgress()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showLoading(loading: Boolean) {
        showLoading()
        setCanLoading(loading)
    }

    /**
     * 设置footShow显示
     */
    fun footShowClickText() {
        if (mFootViewHolder != null) {
            mFootViewHolder?.showClickText()
        }
    }

    /**
     * 显示-已加载全部数据
     */
    fun showNoData() {
        if (mFootViewHolder != null) {
            mFootViewHolder?.setClickable(false)
            mFootViewHolder?.showText("已加载全部数据")
        }
    }

    class FootViewHolder(itemView: View, var context: Context) : BaseViewHolder(itemView, context, false) {
        var llFoot: LinearLayout = find(R.id.ll_foot)
        var progressBar: ProgressBar = find(R.id.progressBar)
        var tvLoad: TextView = find(R.id.tvLoad)

        fun setClickable(clickable: Boolean) {
            llFoot.isClickable = clickable
        }

        fun setTvLoadText(text: String) {
            tvLoad.text = text
        }

        fun showProgress() {
            progressBar.visibility = View.VISIBLE
            tvLoad.text = context.getString(R.string.loading)
        }

        fun showClickText() {
            progressBar.visibility = View.GONE
            tvLoad.text = context.getString(R.string.reload)
        }

        fun showText(text: String) {
            progressBar.visibility = View.GONE
            setTvLoadText(text)
        }
    }

    inner class NotifyObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            if (onScrollListener != null) {
                onScrollListener?.setCanLoad(false)
            }

            if (data != null) {
                if (data.size <= 3) {
                    setShowLoadMore(false)
                } else {
                    setShowLoadMore(true)
                }
            }
        }
    }
}