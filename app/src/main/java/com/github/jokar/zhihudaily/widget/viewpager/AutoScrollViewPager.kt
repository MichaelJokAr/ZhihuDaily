package com.github.jokar.zhihudaily.widget.viewpager

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import java.lang.ref.WeakReference


/**
 * Created by JokAr on 2017/6/22.
 */
class AutoScrollViewPager : ViewPager {
    val DEFAULT_INTERVAL: Int = 1500
    val SCROLL_WHAT = 0
    /**
     * 向左滑动
     */
    val LEFT: Int = 0
    /**
     * 向右滑动
     */
    var RIGHT: Int = 1
    /**
     * 循环到最后或者首位时不进行下一步操作
     */
    val SLIDE_BORDER_MODE_NONE: Int = 0
    /**
     * 周期循环滚动
     */
    val SLIDE_BORDER_MODE_CYCLE: Int = 1
    /**
     * 再滑动到第一或者最后时,把事件传递给父控件
     */
    val SLIDE_BORDER_MODE_TO_PARENT = 2

    /**
     * 每次自动滑动的间隔时间
     */
    var interval: Int = DEFAULT_INTERVAL
    /**
     * 滑动方向，默认向右
     */
    var direction = RIGHT

    /**
     * 是否周期循环,默认为true
     */
    var isCycle = true

    /**
     * 是否触摸时停止自动滑动,默认为true
     */
    var stopScrollWhenTouch = true
    /**
     * 在滑动到第一个或最后一个时该怎么处理
     */
    var slideBorderMode: Int = SLIDE_BORDER_MODE_NONE
    /**
     * 是否使用动画在滑动到一个或最后一个，默认为true
     */
    var isBorderAnimation: Boolean = true
    /**
     * 自动滚动动画的滚动因子,默认为1.0
     */
    var autoScrollFactor: Double = 1.0
    /**
     * 滑动滚动动画的滚动因子,默认为1.0
     */
    val swipeScrollFactor: Double = 1.0

    var handle: MyHandle? = null
    var isAutoScroll = false
    var isStopByTouch = false

    var scroller: CustomDurationScroller? = null

    constructor(context: Context?) : this(context, null) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }


    init {
        init()
    }

    fun init() {
        handle = MyHandle(this)
        setViewPagerScroller()
    }

    fun startAutoScroll() {

        isAutoScroll = true
        sendScrollMessage(((interval + scroller?.duration!! / autoScrollFactor * swipeScrollFactor).toLong()))
    }

    fun startAutoScroll(delayTimeInMills: Long) {
        isAutoScroll = true
        sendScrollMessage(delayTimeInMills)
    }

    fun stopAutoScroll() {
        isAutoScroll = false
        handle?.removeMessages(SCROLL_WHAT)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(ev)
        if (stopScrollWhenTouch) {
            if (action == MotionEvent.ACTION_DOWN && isAutoScroll) {
                isStopByTouch = true
                stopAutoScroll()
            } else if (ev.action == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll()
            }
        }
        return super.dispatchTouchEvent(ev)

    }

    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private fun setViewPagerScroller() {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            val interpolatorField = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolatorField.isAccessible = true

            scroller = CustomDurationScroller(context, interpolatorField.get(null) as Interpolator)
            scrollerField.set(this, scroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 滚动一次
     */
    fun scrollOnce() {
        var totalCount: Int = 0
        if (adapter == null) {
            return
        }
        //
        totalCount = adapter.count
        if (totalCount <= 1) {
            return
        }

        var nextItem = 0
        if (direction == LEFT) {
            nextItem = --currentItem
        } else {
            nextItem = ++currentItem
        }

        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation)
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation)
            }
        } else {
            setCurrentItem(nextItem, true)
        }
    }

    private fun sendScrollMessage(delayTimeInMills: Long) {
        /** remove messages before, keeps one message is running at most  */
        handle?.removeMessages(SCROLL_WHAT)
        handle?.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills)
    }

    inner class MyHandle(viewPager: AutoScrollViewPager) : Handler() {
        var autoScrollView: WeakReference<AutoScrollViewPager> = WeakReference(viewPager)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SCROLL_WHAT -> {
                    var viewPager = autoScrollView.get()
                    if (viewPager != null) {
                        viewPager.scroller?.scrollFactor = viewPager.autoScrollFactor
                        viewPager.scrollOnce()
                        viewPager.scroller?.scrollFactor = viewPager.swipeScrollFactor
                        viewPager.sendScrollMessage((viewPager.interval + viewPager.scroller?.duration!!).toLong())
                    }
                }
            }
        }
    }
}