package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.util.TypedValue

/**
 * Created by JokAr on 2017/7/16.
 */
object CommonView {

    fun selectableItemBackground(context: Context): Int {
        var outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return outValue.resourceId
    }


    fun getColorSchemeResources(): Int {
        return  android.R.color.holo_blue_bright
    }
}