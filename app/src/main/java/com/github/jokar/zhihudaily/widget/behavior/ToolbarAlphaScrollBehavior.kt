package com.github.jokar.zhihudaily.widget.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by JokAr on 2017/6/26.
 */
class ToolbarAlphaScrollBehavior(var context: Context,
                                 attrs: AttributeSet)
    : CoordinatorLayout.Behavior<AppBarLayout>(context, attrs) {


    override fun onInterceptTouchEvent(parent: CoordinatorLayout?, child: AppBarLayout?, ev: MotionEvent?): Boolean {
        return ev == null || super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: AppBarLayout, dependency: View): Boolean {
        val ratio = getCurrentScrollValue(child, dependency).toFloat() / getTotalScrollRange(child, dependency)
        val alpha = 1f - Math.min(1f, Math.max(0f, ratio))
//        val drawableAlpha = (alpha * 255) as Int
        child.alpha = alpha
        return false
    }


    private fun getCurrentScrollValue(child: AppBarLayout, dependency: View): Int {
        return dependency.bottom - child.top
    }

    private fun getTotalScrollRange(child: AppBarLayout, dependency: View): Float {
        return (Math.max(dependency.height, (dependency as AppBarLayout).totalScrollRange) - child.top).toFloat()
    }
}