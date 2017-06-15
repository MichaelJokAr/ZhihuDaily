package com.github.jokar.zhihudaily.model.event.callback

/**
 * Created by JokAr on 2017/6/15.
 */
interface SingleDataCallBack<in T> : BaseCallBack {
    fun data(data: T)
}