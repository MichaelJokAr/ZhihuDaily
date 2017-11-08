package com.github.jokar.zhihudaily.ui.fragment


import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateThemeEvent
import com.github.jokar.zhihudaily.presenter.ThemePresenter
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import com.github.jokar.zhihudaily.ui.adapter.base.AdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.main.ThemeAdapter
import com.github.jokar.zhihudaily.ui.layout.CommonView
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.view.SwipeRefreshLayoutUtil
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.github.jokar.zhihudaily.widget.LoadLayout
import com.github.jokar.zhihudaily.widget.loadLayout
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

/**
 * 主题
 * Created by JokAr on 2017/7/2.
 */
class ThemeFragment : LazyFragment(), SingleDataView<ThemeEntity> {

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    var loadView: LoadLayout? = null

    @Inject
    lateinit var presenter: ThemePresenter

    var idValue: Int = 0

    var adapter: ThemeAdapter? = null
    var themeEntity: ThemeEntity? = null

    override fun onAttach(activity: Activity?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return createView()
    }

    override fun getPresent(): BasePresenter? {
        return presenter
    }

    override fun initViews(view: View) {

        RxBus.getInstance()
                .toMainThreadObservable(this, Lifecycle.Event.ON_DESTROY)
                .subscribe { event ->
                    if (event is UpdateThemeEvent) {
                        idValue = event.id
                        getData()
                    }
                }
    }

    fun getData() {
        presenter.getTheme(idValue, this)
    }

    override fun getDataStart() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)

        if (loadView?.isShown!!) {
            loadView?.visibility = View.GONE
            swipeRefreshLayout?.visibility = View.VISIBLE
        }

    }

    override fun loadData(data: ThemeEntity) {
        themeEntity = data.copy()

        adapter = ThemeAdapter(this, Lifecycle.Event.ON_DESTROY, themeEntity!!)
        recyclerView?.adapter = adapter
        adapter?.clickListener = object : AdapterItemClickListener {
            override fun itemClickListener(position: Int) {
                super.itemClickListener(position)
                //跳转详情页
                var intent = Intent(activity, StoryDetailActivity::class.java)
                intent.putExtra("id", themeEntity!!.stories[position].id)
                startActivity(intent)
            }
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

    /**
     * createView
     */
    fun createView(): View {
        return UI {
            linearLayout {
                //swipeRefreshLayout
                swipeRefreshLayout = swipeRefreshLayout {
                    setColorSchemeColors(CommonView.getColorSchemeResources())
                    setOnRefreshListener {
                        getData()
                    }
                    //recyclerView
                    recyclerView = recyclerView {
                        layoutManager = LinearLayoutManager(context)
                        backgroundColor = Color.parseColor("#f3f3f3")
                    }
                }.lparams(width = matchParent, height = matchParent)

                //loadLayout
                loadView = loadLayout {
                    backgroundColor = Color.parseColor("#eeeeee")
                    retryListener = LoadLayout.RetryListener { getData() }
                }.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter = null
        themeEntity = null
        //view
        swipeRefreshLayout?.setOnRefreshListener { null }
        swipeRefreshLayout = null
        recyclerView = null
        loadView?.retryListener = null
        loadView = null
    }

}