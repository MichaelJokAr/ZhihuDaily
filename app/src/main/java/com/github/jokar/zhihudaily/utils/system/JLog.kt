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

    fun d(value: Long?) {
        XLog.tag(TAG).d(value ?: "")
    }

    fun d(value: Int?) {
        XLog.tag(TAG).d(value ?: "")
    }

    fun d(value: Boolean?) {
        XLog.tag(TAG).d(value ?: "")
    }

    fun w(value: String?) {
        XLog.tag(TAG).w(value ?: "")
    }

    fun w(value: Long?) {
        XLog.tag(TAG).w(value ?: "")
    }

    fun e(e: Throwable) {
        e.printStackTrace()
        e(e.message)
    }

    fun e(value: String?) {
        XLog.tag(TAG).e(value ?: "")
    }

}