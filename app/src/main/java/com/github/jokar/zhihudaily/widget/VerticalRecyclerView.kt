package com.github.jokar.zhihudaily.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by JokAr on 2017/6/19.
 */
class VerticalRecyclerView : RecyclerView {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    var mLastXIntercept = 0
    var mLastYIntercept = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var x: Int = ev.x.toInt()
        var y: Int = ev.y.toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - mLastXIntercept
                var deltaY = y - mLastYIntercept
                //如果是左右滑动
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        mLastXIntercept = x
        mLastYIntercept = y
        return super.dispatchTouchEvent(ev)
    }
}