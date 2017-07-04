package com.github.jokar.zhihudaily.ui.view.common

/**
 * Created by JokAr on 2017/7/2.
 */
interface ListDataView<T> {

    /**
     * 请求数据开始
     */
    fun getDataStart()

    /**
     * 加载数据
     */
    fun loadData(data:ArrayList<T>)

    /**
     * 加载完成
     */
    fun loadComplete()

    /**
     * 请求/加载失败
     */
    fun fail(e:Throwable)
}