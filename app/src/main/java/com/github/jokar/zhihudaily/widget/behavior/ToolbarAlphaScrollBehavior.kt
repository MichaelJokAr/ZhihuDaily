package com.github.jokar.zhihudaily.widget.behavior

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.jokar.zhihudaily.R


/**
 * Created by JokAr on 2017/6/26.
 */
class ToolbarAlphaScrollBehavior(var context: Context,
                                 var attrs: AttributeSet) : CoordinatorLayout.Behavior<Toolbar>(context, attrs) {
    var mStatusBarColorDrawable: ColorDrawable? = null
    var mStatusBarColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
    var mTitleView: TextView? = null
    var searchedForTitleView = false

    init {
        mStatusBarColor = getColorWithAlpha(0f, mStatusBarColor)
        mStatusBarColorDrawable = ColorDrawable(mStatusBarColor)
    }

    fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
        val a = Math.min(255, Math.max(0, (alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and baseColor
        return a + rgb
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: Toolbar, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout?, child: Toolbar?, ev: MotionEvent?): Boolean {
        return ev == null || super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: Toolbar, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            val ratio = getCurrentScrollValue(child, dependency).toFloat() / getTotalScrollRange(child, dependency)
            val alpha = 1f - Math.min(1f, Math.max(0f, ratio))
            val drawableAlpha = (alpha * ViewCompatHelper.DRAWABLE_ALPHA) as Int
            //  Log.i("ToolbarAlphaScrollBehavior", "Alpha: " + alpha);
            if (ViewCompat.isL()) {
                child.background.alpha = drawableAlpha
            } else if (ViewCompatHelper.isKitKat()) {
                val toolbarParent = child.parent as ViewGroup
                if (toolbarParent.childCount == 2) {
                    val count = toolbarParent.childCount
                    for (i in count - 1 downTo 0) {
                        toolbarParent.getChildAt(i).background.alpha = drawableAlpha
                    }
                }
            } else {
                child.background.alpha = drawableAlpha
            }

            //     setStatusBarColor(parent, drawableAlpha);
            if (mTitleView != null) {
                ViewCompat.setAlpha(mTitleView, alpha)
                return false
            }
            if (!searchedForTitleView) {
                mTitleView = ViewCompat..getTitleView(child)
                searchedForTitleView = true
            }

        }
        return false
    }

    private fun setStatusBarColor(parent: CoordinatorLayout, alpha: Int) {
        val statusBarBackground = parent.statusBarBackground as ColorDrawable?
        statusBarBackground!!.color = getColorWithAlpha(alpha.toFloat(), statusBarBackground.color)
        parent.statusBarBackground = statusBarBackground
    }

    private fun getCurrentScrollValue(child: Toolbar, dependency: View): Int {
        return dependency.getBottom() - child.top
    }

    private fun getTotalScrollRange(child: Toolbar, dependency: View): Float {
        return (Math.max(dependency.getHeight(), (dependency as AppBarLayout).totalScrollRange) - child.top).toFloat()
    }
}