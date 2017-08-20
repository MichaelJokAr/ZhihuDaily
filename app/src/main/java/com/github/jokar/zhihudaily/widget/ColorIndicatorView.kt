package com.github.jokar.zhihudaily.widget

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.github.jokar.zhihudaily.R

/**
 * Created by JokAr on 2017/8/19.
 */

open class ColorIndicatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var showCheck: Boolean?
    var colorValue: Int?
    var colorName: String? = null

    val paint: Paint
    val textSize: Int

    var mBitmap: Bitmap? = null
    var mTextRect: Rect? = null

    var mSrcRect: Rect? = null
    var mDestRect: Rect? = null

    init {

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ColorIndicatorView, defStyleAttr,
                0)
        showCheck = typedArray.getBoolean(R.styleable.ColorIndicatorView_showCheck, false)
        colorValue = typedArray.getColor(R.styleable.ColorIndicatorView_colorValue,
                ContextCompat.getColor(context, R.color.colorPrimary))
        colorName = typedArray.getString(R.styleable.ColorIndicatorView_colorName)
        textSize = typedArray.getDimensionPixelSize(R.styleable.ColorIndicatorView_colorNameSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt())

        if (TextUtils.isEmpty(colorName)) {
            colorName = ""
        }

        paint = Paint()
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_check)
        mSrcRect = Rect(0, 0, mBitmap!!.width, mBitmap!!.height)
        mDestRect = Rect(0, 0, mBitmap!!.width, mBitmap!!.height)

        //获得文本宽高
        paint.textSize = textSize.toFloat()
        mTextRect = Rect()
        paint.getTextBounds(colorName, 0, colorName!!.length, mTextRect)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = colorValue!!
        //设置抗锯齿
        paint.isAntiAlias = true

        val with = width.toFloat()
        val height = height.toFloat()
        //园的宽高
        val circleHeight = height - height / 4
        val circleWidth = with - with / 4
        canvas.drawCircle(with / 2, circleHeight / 2, circleWidth / 2, paint)
        //画bitmap
        if (showCheck!!) {
            // 计算左边位置
            val left = (with / 2 - mBitmap!!.width / 2).toInt()
            // 计算上边位置
            val top = (circleHeight / 2 - mBitmap!!.height / 2).toInt()
            mDestRect = Rect(left, top, left + mBitmap!!.width, top + mBitmap!!.height)
            canvas.drawBitmap(mBitmap!!, mSrcRect, mDestRect!!, paint)
        }
        //
        paint.color = Color.BLACK
        canvas.drawText(colorName!!, with / 2 - mTextRect!!.width() / 2, circleHeight + (mTextRect!!.height() / 2).toFloat() + 5f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val withMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val withSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(measureSize(withMode, withSize), measureSize(heightMode, heightSize))
    }


    fun measureSize(mode: Int, size: Int): Int {

        if (mode == View.MeasureSpec.EXACTLY) {
            return size
        } else {
            return resources.getDimensionPixelOffset(R.dimen.colorIndicatorMiniSize)
        }
    }


    open fun getColorValue(): Int {
        return colorValue!!
    }

    open fun setColorValue(colorValue: Int) {
        this.colorValue = colorValue
        invalidate()
    }

    open fun setShowCheck(showCheck: Boolean) {
        this.showCheck = showCheck
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mBitmap != null) {
            mBitmap!!.recycle()
        }
        mBitmap = null

        mTextRect = null
        mDestRect = null
        mSrcRect = null
    }
}
