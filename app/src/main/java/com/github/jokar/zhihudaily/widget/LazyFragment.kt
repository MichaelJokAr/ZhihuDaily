package com.github.jokar.zhihudaily.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by JokAr on 2017/6/21.
 */
abstract class LazyFragment : RxFragment() {
    /**
     * 判断是否已经初始化
     */
    private var isCreateView = false
    /**
     * 判断是否已加载过数据
     */
    private var isLoadData = false

    abstract fun getView(inflater: LayoutInflater, container: ViewGroup): View

    abstract fun initViews(view: View)

    abstract fun getPresent(): BasePresenter?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = getView(inflater, container!!)

        initViews(view)
        isCreateView = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (userVisibleHint) {
            loadData()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isCreateView && !isLoadData) {
            loadData()
        }
    }

    protected open fun loadData() {
        if (!isLoadData) {
            isLoadData = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresent()?.destroy()
    }
}