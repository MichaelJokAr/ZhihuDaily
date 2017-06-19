package com.github.jokar.zhihudaily.utils.system

import com.elvishew.xlog.XLog

/**
 * Created by JokAr on 2017/6/14.
 */
object JLog {
    val TAG: String = "ZhihuDaily"

    fun d(value: String?) {
        XLog.tag(TAG).d(value ?: "")
    }

    fun e(e: Throwable) {
        e.printStackTrace()
        e(e.message)
    }

    fun e(value: String?) {
        XLog.tag(TAG).e(value ?: "")
    }
}