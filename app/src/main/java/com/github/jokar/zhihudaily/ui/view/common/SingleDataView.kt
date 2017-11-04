package com.github.jokar.zhihudaily.ui.view.common

/**
 * Created by JokAr on 2017/6/25.
 */
interface SingleDataView<T> {
    /**
     * 请求数据开始
     */
    fun getDataStart()
    /**
     * 加载数据
     */
    fun loadData(data: T)
    /**
     * 加载完成
     */
    fun loadComplete()
    /**
     * 请求/加载失败
     */
    fun fail(e:Throwable)
}