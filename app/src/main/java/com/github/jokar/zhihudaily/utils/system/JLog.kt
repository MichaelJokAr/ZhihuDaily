package com.github.jokar.zhihudaily.utils.system

import android.util.Log

/**
 * Created by JokAr on 2017/6/14.
 */
object JLog {
    val TAG: String = "ZhihuDaily"

    fun d(value: String?) {
        Log.d(TAG, value ?: "")
    }

    fun e(e: Throwable) {
        e.printStackTrace()
        e(e.message)
    }

    fun e(value: String?) {
        Log.e(TAG, value ?: "")
    }
}