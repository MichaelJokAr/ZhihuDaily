package com.github.jokar.zhihudaily.utils.view

import android.support.v4.widget.SwipeRefreshLayout
import com.github.jokar.zhihudaily.utils.system.JLog

/**
 * Created by JokAr on 2017/6/23.
 */
object SwipeRefreshLayoutUtil {
    fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout?,
                      refreshing: Boolean) {
        try {
            swipeRefreshLayout?.post { swipeRefreshLayout.isRefreshing = refreshing }
        } catch (e: Exception) {
            JLog.e(e)
        }

    }

    fun setColor(swipeRefreshLayout: SwipeRefreshLayout?) {
        try {
            swipeRefreshLayout?.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setColor(swipeRefreshLayout: SwipeRefreshLayout?,color:Int) {
        try {
            swipeRefreshLayout?.setColorSchemeColors(color)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}