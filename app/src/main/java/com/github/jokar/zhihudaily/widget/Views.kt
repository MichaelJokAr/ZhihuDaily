package com.github.jokar.zhihudaily.widget

import android.view.ViewManager
import com.like.LikeButton
import com.rd.PageIndicatorView
import org.jetbrains.anko.custom.ankoView

/**
 * Created by JokAr on 2017/7/17.
 */
inline fun ViewManager.likeButton(theme: Int = 0) = likeButton(theme) {}

inline fun ViewManager.likeButton(theme: Int = 0, init: LikeButton.() -> Unit) = ankoView({ LikeButton(it) }, theme, init)


inline fun ViewManager.circleImageView(theme: Int = 0) = circleImageView(theme) {}

inline fun ViewManager.circleImageView(theme: Int = 0, init: CircleImageView.() -> Unit) = ankoView({ CircleImageView(it) }, theme, init)

inline fun ViewManager.pageIndicatorView(theme: Int = 0) = pageIndicatorView(theme) {}

inline fun ViewManager.pageIndicatorView(theme: Int = 0, init: PageIndicatorView.() -> Unit) = ankoView({ PageIndicatorView(it) }, theme, init)

inline fun ViewManager.motorTrackerViewPager(theme: Int = 0) = motorTrackerViewPager(theme) {}

inline fun ViewManager.motorTrackerViewPager(theme: Int = 0, init: MotorTrackerViewPager.() -> Unit) = ankoView({ MotorTrackerViewPager(it) }, theme, init)

inline fun ViewManager.verticalRecyclerView(theme: Int = 0) = verticalRecyclerView(theme) {}

inline fun ViewManager.verticalRecyclerView(theme: Int = 0, init: VerticalRecyclerView.() -> Unit) = ankoView({ VerticalRecyclerView(it) }, theme, init)

inline fun ViewManager.loadLayout(theme: Int = 0) = loadLayout(theme) {}

inline fun ViewManager.loadLayout(theme: Int = 0, init: LoadLayout.() -> Unit) = ankoView({ LoadLayout(it) }, theme, init)