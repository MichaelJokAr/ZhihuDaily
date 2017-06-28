package com.github.jokar.zhihudaily.utils.system

import android.os.Build
import android.view.View
import android.view.ViewTreeObserver



/**
 * Created by JokAr on 2017/6/28.
 */
object ScrollUtils {
    /**
     * Return a float value within the range.
     *
     * This is just a wrapper for Math.min() and Math.max().
     * This may be useful if you feel it confusing ("Which is min and which is max?").

     * @param value    The target value.
     * *
     * @param minValue Minimum value. If value is less than this, minValue will be returned.
     * *
     * @param maxValue Maximum value. If value is greater than this, maxValue will be returned.
     * *
     * @return Float value limited to the range.
     */
    fun getFloat(value: Float, minValue: Float, maxValue: Float): Float {
        return Math.min(maxValue, Math.max(minValue, value))
    }

    /**
     * Create a color integer value with specified alpha.
     *
     * This may be useful to change alpha value of background color.

     * @param alpha     Alpha value from 0.0f to 1.0f.
     * *
     * @param baseColor Base color. alpha value will be ignored.
     * *
     * @return A color with alpha made from base color.
     */
    fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
        val a = Math.min(255, Math.max(0, (alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and baseColor
        return a + rgb
    }

    /**
     * Add an OnGlobalLayoutListener for the view.
     *
     * This is just a convenience method for using `ViewTreeObserver.OnGlobalLayoutListener()`.
     * This also handles removing listener when onGlobalLayout is called.

     * @param view     The target view to add global layout listener.
     * *
     * @param runnable Runnable to be executed after the view is laid out.
     */
    fun addOnGlobalLayoutListener(view: View, runnable: Runnable) {
        val vto = view.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                runnable.run()
            }
        })
    }

    /**
     * Mix two colors.
     *
     * `toColor` will be `toAlpha/1` percent,
     * and `fromColor` will be `(1-toAlpha)/1` percent.

     * @param fromColor First color to be mixed.
     * *
     * @param toColor   Second color to be mixed.
     * *
     * @param toAlpha   Alpha value of toColor, 0.0f to 1.0f.
     * *
     * @return Mixed color value in ARGB. Alpha is fixed value (255).
     */
    fun mixColors(fromColor: Int, toColor: Int, toAlpha: Float): Int {
        val fromCmyk = ScrollUtils.cmykFromRgb(fromColor)
        val toCmyk = ScrollUtils.cmykFromRgb(toColor)
        val result = FloatArray(4)
        for (i in 0..3) {
            result[i] = Math.min(1f, fromCmyk[i] * (1 - toAlpha) + toCmyk[i] * toAlpha)
        }
        return 0xff000000.toInt() + (0x00ffffff and ScrollUtils.rgbFromCmyk(result))
    }

    /**
     * Convert RGB color to CMYK color.

     * @param rgbColor Target color.
     * *
     * @return CMYK array.
     */
    fun cmykFromRgb(rgbColor: Int): FloatArray {
        val red = 0xff0000 and rgbColor shr 16
        val green = 0xff00 and rgbColor shr 8
        val blue = 0xff and rgbColor
        val black = Math.min(1.0f - red / 255.0f, Math.min(1.0f - green / 255.0f, 1.0f - blue / 255.0f))
        var cyan = 1.0f
        var magenta = 1.0f
        var yellow = 1.0f
        if (black != 1.0f) {
            // black 1.0 causes zero divide
            cyan = (1.0f - red / 255.0f - black) / (1.0f - black)
            magenta = (1.0f - green / 255.0f - black) / (1.0f - black)
            yellow = (1.0f - blue / 255.0f - black) / (1.0f - black)
        }
        return floatArrayOf(cyan, magenta, yellow, black)
    }

    /**
     * Convert CYMK color to RGB color.
     * This method doesn't check if cmyk is not null or have 4 elements in array.

     * @param cmyk Target CYMK color. Each value should be between 0.0f to 1.0f,
     * *             and should be set in this order: cyan, magenta, yellow, black.
     * *
     * @return ARGB color. Alpha is fixed value (255).
     */
    fun rgbFromCmyk(cmyk: FloatArray): Int {
        val cyan = cmyk[0]
        val magenta = cmyk[1]
        val yellow = cmyk[2]
        val black = cmyk[3]
        val red = ((1.0f - Math.min(1.0f, cyan * (1.0f - black) + black)) * 255).toInt()
        val green = ((1.0f - Math.min(1.0f, magenta * (1.0f - black) + black)) * 255).toInt()
        val blue = ((1.0f - Math.min(1.0f, yellow * (1.0f - black) + black)) * 255).toInt()
        return (0xff and red shl 16) + (0xff and green shl 8) + (0xff and blue)
    }
}