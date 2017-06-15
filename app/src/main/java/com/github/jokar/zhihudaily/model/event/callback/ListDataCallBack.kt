package com.github.jokar.zhihudaily.model.event.callback

/**
 * Created by JokAr on 2017/6/15.
 */
interface ListDataCallBack<T> : BaseCallBack {

    fun data(data: ArrayList<T>)
}