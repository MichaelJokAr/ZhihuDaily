package com.github.jokar.zhihudaily.ui.view.common

/**
 * Created by JokAr on 2017/6/25.
 */
interface SingleDataView<T> {

    fun getDataStart()

    fun loadData(data: T)

    fun loadComplete()

    fun fail(e:Throwable)
}