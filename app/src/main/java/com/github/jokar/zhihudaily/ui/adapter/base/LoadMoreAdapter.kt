package com.github.jokar.zhihudaily.ui.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.github.jokar.zhihudaily.widget.recyclerView.RecyclerOnScrollListener
import com.trello.rxlifecycle2.LifecycleTransformer
import com.github.jokar.zhihudaily.utils.system.JLog



/**
 * Created by JokAr on 2017/6/21.
 */
abstract class LoadMoreAdapter<T>(var context: Context,
                                  var data: ArrayList<T>,
                                  var transformer: LifecycleTransformer<Any>) : RecyclerView.Adapter<BaseViewHolder>() {
    val TYPE_FOOTVIEW: Int = 100


    var inflater: LayoutInflater? = null
    var recyclerView: RecyclerView? = null
    var onScrollListener: RecyclerOnScrollListener? = null
    var itemClickListener:LoadMoreAdapterItemClickListener? = null
    var mNotifyObserver:NotifyObserver
    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView

        onScrollListener = object :RecyclerOnScrollListener(this.recyclerView!!){
            override fun onLoadMore(currentPage: Int) {
                itemClickListener?.loadMore()
            }
        }

        this.recyclerView?.addOnScrollListener(onScrollListener)
        onScrollListener?.setCanLoade(false)

    }


    internal inner class NotifyObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            if (onScrollListener != null) {
                onScrollListener?.setCanLoade(false)
            }
            if (data != null) {
                if (data?.size <= 3) {
                    setShowLoadMore(false)
                } else {
                    setShowLoadMore(true)
                }
                JLog.d("onChange")
            }
        }
    }

}