/*
 * Copyright (C) 2016 JokAr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jokar.zhihudaily.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 可以控制是否滑动的viewPager
 *
 *  * author: jokAr
 *  * date: 2015/12/14,12:22
 *  * mail: langzaitianyag@vip.qq.com
 *
 */
open class MotorTrackerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet?)
    : ViewPager(context, attrs) {
    constructor(context: Context) : this(context, null)

    var value: Boolean = false

    init {
        this.value = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (this.value) {
            return super.onTouchEvent(event)
        }

        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (this.value) {
            return super.onInterceptTouchEvent(event)
        }

        return false
    }

    /**
     * Custom implementation to enable or not swipe :)
     * 设置viewPager是否可以滑动
     * @param value
     * *            true to enable swipe, false otherwise.
     */
    fun setPagingEnabled(value: Boolean) {
        this.value = value
    }

}
