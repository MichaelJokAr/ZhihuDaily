package com.github.jokar.zhihudaily.ui.fragment


import android.app.Activity
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateThemeEvent
import com.github.jokar.zhihudaily.presenter.ThemePresenter
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.view.SwipeRefreshLayoutUtil
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.github.jokar.zhihudaily.widget.LoadLayout
import com.trello.rxlifecycle2.android.FragmentEvent
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * 主题
 * Created by JokAr on 2017/7/2.
 */
class ThemeFragment : LazyFragment(), SingleDataView<ThemeEntity> {

    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.nestedScrollView)
    lateinit var nestedScrollView: NestedScrollView
    @BindView(R.id.image)
    lateinit var image: ImageView
    @BindView(R.id.tvTiTle)
    lateinit var tvTiTle: TextView
    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.loadView)
    lateinit var loadView: LoadLayout

    @Inject
    lateinit var presenter: ThemePresenter

    var idValue: Int = 0

    override fun onAttach(activity: Activity?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    var bind: Unbinder? = null
    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragment_theme, container, false)
    }

    override fun initViews(view: View) {
        bind = ButterKnife.bind(this, view)

        SwipeRefreshLayoutUtil.setColor(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.setOnRefreshListener({
            getData()
        })

        loadView.retryListener = LoadLayout.RetryListener {
            getData()
        }

        RxBus.getInstance()
                .toMainThreadObservable(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe {
                    event ->
                    if (event is UpdateThemeEvent) {
                        idValue = event.id
                        getData()
                    }
                }
    }

    fun getData() {
        presenter.getTheme(idValue, bindUntilEvent(FragmentEvent.DESTROY_VIEW))
    }

    override fun getDataStart() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, true)

        if (loadView.isShown) {
            loadView.visibility = View.GONE
            swipeRefreshLayout.visibility = View.VISIBLE
        }
    }

    override fun loadData(data: ThemeEntity) {

    }

    override fun loadComplete() {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)
    }

    override fun fail(e: Throwable) {
        SwipeRefreshLayoutUtil.setRefreshing(swipeRefreshLayout, false)
        if (!loadView.isShown) {
            loadView.visibility = View.VISIBLE
            swipeRefreshLayout.visibility = View.GONE
        }
        loadView.showError(e.message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
        bind?.unbind()
    }

}