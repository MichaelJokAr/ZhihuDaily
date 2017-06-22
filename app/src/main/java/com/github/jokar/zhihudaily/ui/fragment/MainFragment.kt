package com.github.jokar.zhihudaily.ui.fragment

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.presenter.MainFragmentPresenter
import com.github.jokar.zhihudaily.ui.adapter.base.LoadMoreAdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.main.StoryAdapter
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import com.github.jokar.zhihudaily.utils.system.JLog
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.trello.rxlifecycle2.android.FragmentEvent
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * 首页
 * Created by JokAr on 2017/6/19.
 */
class MainFragment : LazyFragment(), StoryView {
    lateinit
    var recyclerView: RecyclerView
    @Inject
    lateinit var presenter: MainFragmentPresenter

    var adapter: StoryAdapter? = null
    var arrayList: ArrayList<StoryEntities>? = null

    override fun onAttach(activity: Activity?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragmnet_main, container, false)
    }

    override fun loadData() {
        super.loadData()
        presenter?.getLatestStory(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
    }

    /**
     * 请求数据开始
     */
    override fun getDataStart() {

    }

    /**
     * 加载数据
     */
    override fun loadData(data: LatestStory) {
        JLog.d(data.toString())
        if (adapter == null) {
            arrayList = data.stories?.clone() as ArrayList<StoryEntities>
            adapter = StoryAdapter(context, arrayList!!, bindUntilEvent(FragmentEvent.DESTROY_VIEW),
                    data.top_stories!!, childFragmentManager)
            recyclerView.adapter = adapter
            adapter?.itemClickListener = object : LoadMoreAdapterItemClickListener {
                override fun itemClickListener(position: Int) {

                }

                override fun loadMore() {

                }

                override fun footViewClick() {

                }

            }
        } else {
            arrayList?.clear()
            arrayList?.addAll(data.stories?.clone() as ArrayList<StoryEntities>)
            adapter?.notifyDataSetChanged()
        }


    }

    /**
     * 加载完成
     */
    override fun loadComplete() {

    }

    /**
     * 请求/加载失败
     */
    override fun fail(e: Throwable) {

    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData(data: ArrayList<StoryEntities>) {

    }

    /**
     * 请求/加载更多失败
     */
    override fun loadMoreFail(e: Throwable) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.destroy()
    }
}