package com.github.jokar.zhihudaily.model.event.callback

import com.github.jokar.zhihudaily.utils.system.JLog

/**
 * Created by JokAr on 2017/6/15.
 */
interface BaseCallBack {
    fun onStart() {

    }

    fun onComplete() {

    }

    fun onError(e: Throwable) {
        JLog.e(e)
    }
}