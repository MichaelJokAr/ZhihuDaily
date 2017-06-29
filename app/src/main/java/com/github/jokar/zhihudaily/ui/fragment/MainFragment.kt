package com.github.jokar.zhihudaily.ui.fragment

import android.app.Activity
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateToolbarTitleEvent
import com.github.jokar.zhihudaily.presenter.MainFragmentPresenter
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import com.github.jokar.zhihudaily.ui.adapter.base.LoadMoreAdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.main.StoryAdapter
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import com.github.jokar.zhihudaily.utils.view.SwipeRefreshLayoutUtil
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.trello.rxlifecycle2.android.FragmentEvent
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * 首页
 * Created by JokAr on 2017/6/19.
 */
class MainFragment : LazyFragment(), StoryView {

    @Inject
    lateinit var presenter: MainFragmentPresenter

    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    var bind: Unbinder? = null
    var adapter: StoryAdapter? = null
    var arrayList: ArrayList<StoryEntities>? = null

    override fun onAttach(activity: Activity?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun initViews(view: View) {
        bind = ButterKnife.bind(this, view)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.setOnRefreshListener({
            presenter.getLatestStory(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        }
        )
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragmnet_main, container, false)
    }

    override fun loadData() {
        super.loadData()
        presenter.getLatestStory(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
    }

    /**
     * 请求数据开始
     */
    override fun getDataStart() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)
    }

    /**
     * 加载数据
     */
    override fun loadData(data: LatestStory) {

        activity.runOnUiThread {
            if (adapter == null) {
                arrayList = data.stories?.clone() as ArrayList<StoryEntities>
                adapter = StoryAdapter(context, arrayList!!, bindUntilEvent(FragmentEvent.DESTROY_VIEW),
                        data.top_stories!!)
                recyclerView.adapter = adapter
                adapter?.itemClickListener = object : LoadMoreAdapterItemClickListener {
                    override fun firstCompletelyVisibleItem(position: Int) {
                        super.firstCompletelyVisibleItem(position)
                        //更新
                        RxBus.getInstance().post(UpdateToolbarTitleEvent(arrayList!![position].dateString))
                    }

                    override fun itemClickListener(position: Int) {
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
                arrayList?.addAll(data.stories?.clone() as ArrayList<StoryEntities>)
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
        activity.runOnUiThread({
            SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)
        })
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData(data: ArrayList<StoryEntities>) {
        val size = data.size
        arrayList?.addAll(data)
        adapter?.notifyItemRangeInserted(arrayList?.size!! - size, size)

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

    override fun onDestroyView() {
        super.onDestroyView()
        bind?.unbind()
        arrayList = null
        presenter?.destroy()
    }
}