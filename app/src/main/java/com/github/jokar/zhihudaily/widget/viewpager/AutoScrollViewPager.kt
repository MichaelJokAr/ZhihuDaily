package com.github.jokar.zhihudaily.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.utils.system.JLog


/**
 * Created by JokAr on 2017/6/22.
 */
open class AutoScrollViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet?)
    : ViewPager(context, attrs) {


    private val default_duration = 2000
    var duration = default_duration
    //是否自动滚动
    private var autoScroll = true
    //是否循环滚动
    private var indeterminate = true

    private var startX = 0f

    constructor(context: Context) : this(context, null)

    init {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollViewPager)

        duration = typeArray.getInt(R.styleable.AutoScrollViewPager_duration, default_duration)
        setAutoScrollEnabled(typeArray.getBoolean(R.styleable.AutoScrollViewPager_autoScroll, true))
        indeterminate = typeArray.getBoolean(R.styleable.AutoScrollViewPager_indeterminate, true)

        typeArray.recycle()

    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (!isShown) {
                return
            }
            if (currentItem == adapter.count - 1) {
//                currentItem = 0
                setCurrentItem(0, false)
            } else {
                currentItem += 1
            }
            postDelayed(this, duration.toLong())
        }
    }

    internal fun setAutoScrollEnabled(value: Boolean) {

        autoScroll = value

        if (autoScroll) {
            startAutoScroll()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setAutoScrollEnabled(autoScroll)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAutoScroll()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            setAutoScrollEnabled(autoScroll)
        } else {
            stopAutoScroll()
        }
    }

    override fun onInterceptHoverEvent(event: MotionEvent): Boolean {
        try {
            var action = event.actionMasked
            when (action) {
                MotionEvent.ACTION_DOWN -> startX = event.x
            }
            return super.onInterceptHoverEvent(event)
        } catch (e: Exception) {
            JLog.e(e)
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action

        try {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    //按下手指停止自动滚动
                    stopAutoScroll()
                    startX = ev.x
                }
                MotionEvent.ACTION_UP -> {
                    if (indeterminate
                            && (currentItem == 0 || currentItem == adapter.count - 1)) {

                        if (currentItem == adapter.count - 1
                                && (ev.x < startX && Math.abs(ev.x - startX) > 50)) {
                            postDelayed({ setCurrentItem(0, false) },
                                    100)
                        }
                    } else {
                        startX = 0f
                    }
                    //移开手指开始自动滚动
                    startAutoScroll()
                }
            }
            return super.onTouchEvent(ev)
        } catch (e: Exception) {
            JLog.e(e)
        }

        return false
    }

    private fun startAutoScroll() {
        stopAutoScroll()
        postDelayed(autoScrollRunnable, duration.toLong())
    }

    private fun stopAutoScroll() {
        removeCallbacks(autoScrollRunnable)
    }
}