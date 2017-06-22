package com.github.jokar.zhihudaily.widget.viewpager

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by JokAr on 2017/6/22.
 */
class CustomDurationScroller : Scroller {
    constructor(context: Context) : super(context)
    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator)

    var scrollFactor: Double = 1.0

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * scrollFactor).toInt())
    }
}