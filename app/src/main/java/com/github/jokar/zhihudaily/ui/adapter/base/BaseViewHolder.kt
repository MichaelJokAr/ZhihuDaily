package com.github.jokar.zhihudaily.ui.adapter.base

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.jokar.zhihudaily.R

/**
 * Created by JokAr on 2017/6/19.
 */
open class BaseViewHolder : RecyclerView.ViewHolder {

    constructor(itemView: View, context: Context, addBG: Boolean?) : super(itemView) {
        if (addBG != null && addBG) {
            addBG(context)
        }
    }

    constructor(itemView: View, context: Context) : super(itemView) {
        addBG(context)
    }

    fun addBG(context: Context) {
        val attrs = intArrayOf(R.attr.selectableItemBackground)
        val typedArray = context.obtainStyledAttributes(attrs)
        val backgroundResource = typedArray.getResourceId(0, 0)
        this.itemView.setBackgroundResource(backgroundResource)
    }

    fun <T : View> find(@IdRes id: Int): T {

        return itemView.findViewById(id)
    }
}