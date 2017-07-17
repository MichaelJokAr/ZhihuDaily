package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateCollectionEvent
import com.github.jokar.zhihudaily.presenter.CollectionPresenter
import com.github.jokar.zhihudaily.ui.adapter.base.AdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.main.CollectionAdapter
import com.github.jokar.zhihudaily.ui.layout.CommonView
import com.github.jokar.zhihudaily.ui.view.common.ListDataView
import com.github.jokar.zhihudaily.utils.view.SwipeRefreshLayoutUtil
import com.github.jokar.zhihudaily.widget.LoadLayout
import com.github.jokar.zhihudaily.widget.loadLayout
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.common_toolbar.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.include
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

/**
 * 收藏activity
 * Created by JokAr on 2017/7/4.
 */
class CollectionActivity : BaseActivity(), ListDataView<StoryEntity> {

    @Inject
    lateinit var presenter: CollectionPresenter

    var arrayList: ArrayList<StoryEntity>? = null
    var adapter: CollectionAdapter? = null

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    var loadView: LoadLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        createView()
        initToolbar(toolbar, "我的收藏")

        init()
    }

    fun createView() {
        coordinatorLayout {
            include<View>(R.layout.common_toolbar)
            linearLayout {
                orientation = LinearLayout.VERTICAL

                swipeRefreshLayout = swipeRefreshLayout {
                    setColorSchemeResources(CommonView.getColorSchemeResources())
                    setOnRefreshListener {
                        getData()
                    }
                    //recyclerView
                    recyclerView = recyclerView {
                        id = R.id.recyclerView
                        backgroundColor = Color.parseColor("#f3f3f3")
                        isVerticalScrollBarEnabled = true
                        layoutManager = LinearLayoutManager(this@CollectionActivity)
                    }

                }.lparams(width = matchParent, height = matchParent)

                loadView = loadLayout {
                    backgroundColor = Color.parseColor("#eeeeee")
                    retryListener = LoadLayout.RetryListener { getData() }
                }.lparams(width = matchParent, height = matchParent)

            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }

    override fun onWindowInitialized() {
        super.onWindowInitialized()
        getData()
    }


    private fun init() {

        //详细页面修改收藏操作
        RxBus.getInstance()
                .toMainThreadObservable(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    event ->
                    if (event is UpdateCollectionEvent) {
                        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)
                        getData()
                    }
                }
    }

    fun getData() {
        presenter.getCollections(bindUntilEvent(ActivityEvent.DESTROY))
    }

    override fun getDataStart() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)
        if (loadView?.isShown!!) {
            loadView?.visibility = View.GONE
            swipeRefreshLayout?.visibility = View.VISIBLE
        }
    }

    override fun loadData(data: ArrayList<StoryEntity>) {
        if (adapter == null) {
            arrayList = data.clone() as ArrayList<StoryEntity>
            adapter = CollectionAdapter(this, bindUntilEvent(ActivityEvent.DESTROY), arrayList)
            recyclerView?.adapter = adapter
            adapter?.clickListener = object : AdapterItemClickListener {
                override fun itemClickListener(position: Int) {
                    //跳转详情页
                    var intent = Intent(this@CollectionActivity, StoryDetailActivity::class.java)
                    intent.putExtra("id", arrayList?.get(position)?.id)
                    startActivity(intent)
                }
            }
        } else {
            arrayList?.clear()
            arrayList?.addAll(data.clone() as ArrayList<StoryEntity>)
            adapter?.notifyDataSetChanged()
        }

    }

    override fun loadComplete() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)
    }

    override fun fail(e: Throwable) {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)
        if (!loadView?.isShown!!) {
            loadView?.visibility = View.VISIBLE
            swipeRefreshLayout?.visibility = View.GONE
        }
        loadView?.showError(e.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
        arrayList = null
        adapter = null
    }
}