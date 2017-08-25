package com.github.jokar.zhihudaily.widget

import android.view.ViewManager
import com.github.jokar.zhihudaily.widget.viewpager.AutoScrollViewPager
import com.github.jokar.zhihudaily.widget.viewpager.MotorTrackerViewPager
import com.like.LikeButton
import com.rd.PageIndicatorView
import org.jetbrains.anko.custom.ankoView

/**
 * Created by JokAr on 2017/7/17.
 */
inline fun ViewManager.likeButton(theme: Int = 0) = likeButton(theme) {}

inline fun ViewManager.likeButton(theme: Int = 0, init: LikeButton.() -> Unit) = ankoView({ LikeButton(it) }, theme, init)

//CircleImageView
inline fun ViewManager.circleImageView(theme: Int = 0) = circleImageView(theme) {}

inline fun ViewManager.circleImageView(theme: Int = 0, init: CircleImageView.() -> Unit) = ankoView({ CircleImageView(it) }, theme, init)

//PageIndicatorView
inline fun ViewManager.pageIndicatorView(theme: Int = 0) = pageIndicatorView(theme) {}

inline fun ViewManager.pageIndicatorView(theme: Int = 0, init: PageIndicatorView.() -> Unit) = ankoView({ PageIndicatorView(it) }, theme, init)

//MotorTrackerViewPager
inline fun ViewManager.motorTrackerViewPager(theme: Int = 0) = motorTrackerViewPager(theme) {}

inline fun ViewManager.motorTrackerViewPager(theme: Int = 0, init: MotorTrackerViewPager.() -> Unit) = ankoView({ MotorTrackerViewPager(it) }, theme, init)

//VerticalRecyclerView
inline fun ViewManager.verticalRecyclerView(theme: Int = 0) = verticalRecyclerView(theme) {}

inline fun ViewManager.verticalRecyclerView(theme: Int = 0, init: VerticalRecyclerView.() -> Unit) = ankoView({ VerticalRecyclerView(it) }, theme, init)

//LoadLayout
inline fun ViewManager.loadLayout(theme: Int = 0) = loadLayout(theme) {}

inline fun ViewManager.loadLayout(theme: Int = 0, init: LoadLayout.() -> Unit) = ankoView({ LoadLayout(it) }, theme, init)

//autoScrollViewPager
inline fun ViewManager.autoScrollViewPager(theme: Int = 0) = autoScrollViewPager(theme) {}

inline fun ViewManager.autoScrollViewPager(theme: Int = 0, init: AutoScrollViewPager.() -> Unit) = ankoView({ AutoScrollViewPager(it) }, theme, init)
