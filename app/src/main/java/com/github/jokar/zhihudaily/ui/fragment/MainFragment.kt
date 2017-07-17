package com.github.jokar.zhihudaily.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateStoryScrollEvent
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateToolbarTitleEvent
import com.github.jokar.zhihudaily.presenter.MainFragmentPresenter
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import com.github.jokar.zhihudaily.ui.adapter.base.LoadMoreAdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.main.StoryAdapter
import com.github.jokar.zhihudaily.ui.layout.CommonView
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import com.github.jokar.zhihudaily.utils.view.SwipeRefreshLayoutUtil
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.github.jokar.zhihudaily.widget.LoadLayout
import com.trello.rxlifecycle2.android.FragmentEvent
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

/**
 * 首页
 * Created by JokAr on 2017/6/19.
 */
class MainFragment : LazyFragment(), StoryView {

    @Inject
    lateinit var presenter: MainFragmentPresenter

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    var loadView: LoadLayout? = null

    var adapter: StoryAdapter? = null
    var arrayList: ArrayList<StoryEntity>? = null

    override fun onAttach(activity: Activity?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun initViews(view: View) {
        loadView = view.find(R.id.loadView)
        loadView?.retryListener = LoadLayout.RetryListener { getData() }

        RxBus.getInstance()
                .toMainThreadObservable(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe {
                    event ->
                    if (event is UpdateStoryScrollEvent) {
                        recyclerView?.scrollToPosition(0)
                    }
                }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return createView()
    }

    override fun loadData() {
        super.loadData()
        getData()
    }

    private fun getData() {
        presenter.getLatestStory(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
    }

    /**
     * 请求数据开始
     */
    override fun getDataStart() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)
        if (loadView?.isShown!!) {
            loadView?.visibility = View.GONE
            swipeRefreshLayout?.visibility = View.VISIBLE
        }
    }

    /**
     * 加载数据
     */
    override fun loadData(data: LatestStory) {

        activity.runOnUiThread {
            if (adapter == null) {
                arrayList = data.stories?.clone() as ArrayList<StoryEntity>
                adapter = StoryAdapter(context, arrayList!!, bindUntilEvent(FragmentEvent.DESTROY_VIEW),
                        data.top_stories!!)
                recyclerView?.adapter = adapter
                adapter?.headClickListener = object : StoryAdapter.HeadClickListener {
                    override fun itemClick(position: Int) {
                        //跳转详情页
                        var intent = Intent(activity, StoryDetailActivity::class.java)
                        intent.putExtra("id", data.top_stories!![position].id)
                        startActivity(intent)
                    }
                }
                adapter?.itemClickListener = object : LoadMoreAdapterItemClickListener {
                    override fun firstCompletelyVisibleItem(position: Int) {
                        super.firstCompletelyVisibleItem(position)
                        //更新
                        RxBus.getInstance().post(UpdateToolbarTitleEvent(arrayList!![position].dateString))
                    }

                    override fun itemClickListener(position: Int) {
                        //更新已读
                        if (arrayList!![position].read == 0) {
                            arrayList!![position].read = 1
                            presenter.updateStory(arrayList!![position], bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                            adapter?.notifyItemChanged(position)
                        }
                        //跳转详情页
                        var intent = Intent(activity, StoryDetailActivity::class.java)
                        intent.putExtra("id", arrayList!![position].id)
                        startActivity(intent)
                    }

                    override fun loadMore() {
                        getMoreData()
                    }

                    override fun footViewClick() {
                        adapter?.setFootClickable(false)
                        getMoreData()
                    }

                }
            } else {
                arrayList?.clear()
                arrayList?.addAll(data.stories?.clone() as ArrayList<StoryEntity>)
                adapter?.topStories = data.top_stories!!
                adapter?.notifyDataSetChanged()
            }
        }

    }

    /**
     * 加载完成
     */
    override fun loadComplete() {
        activity.runOnUiThread { SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false) }

    }

    /**
     * 请求/加载失败
     */
    override fun fail(e: Throwable) {
        activity.runOnUiThread {
            SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)

            if (!loadView?.isShown!!) {
                loadView?.visibility = View.VISIBLE
                swipeRefreshLayout?.visibility = View.GONE
            }
            loadView?.showError(e.message)
        }
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData(data: ArrayList<StoryEntity>) {
        activity.runOnUiThread {
            val size = data.size
            arrayList?.addAll(data)
            adapter?.notifyItemRangeInserted(arrayList?.size!! - size, size)
        }

    }

    /**
     * 请求/加载更多失败
     */
    override fun loadMoreFail(e: Throwable) {
        adapter?.setFootClickable(true)
    }

    /**
     * 获取下一天数据
     */
    fun getMoreData() {

        presenter?.getNextDayStory(arrayList?.get(arrayList?.size!! - 1)?.date!!, bindUntilEvent(FragmentEvent.DESTROY_VIEW))
    }

    /**
     * createView
     */
    fun createView(): View {
        return UI {
            linearLayout {
                //swipeRefreshLayout
                swipeRefreshLayout = swipeRefreshLayout {
                    setColorSchemeResources(CommonView.getColorSchemeResources())
                    setOnRefreshListener {
                        getData()
                    }
                    //recyclerView
                    recyclerView = recyclerView {
                        layoutManager = LinearLayoutManager(context)
                        backgroundColor = Color.parseColor("#f3f3f3")
                    }
                }.lparams(width = matchParent, height = matchParent)

                include<View>(R.layout.common_load)
            }
        }.view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swipeRefreshLayout?.setOnRefreshListener { null }
        swipeRefreshLayout = null
        recyclerView = null
        loadView = null
        arrayList = null
        presenter?.destroy()
    }
}