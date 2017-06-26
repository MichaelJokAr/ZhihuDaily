package com.github.jokar.zhihudaily.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet

/**
 * Created by JokAr on 2017/6/25.
 */
class CustomerNestedScrollView :NestedScrollView{
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(x, y, oldX, oldY)

    }


}