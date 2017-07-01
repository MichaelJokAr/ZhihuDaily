package com.github.jokar.zhihudaily.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.github.jokar.zhihudaily.R

/**
 * Created by JokAr on 2017/7/1.
 */

class LoadLayout @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet?,
                                           defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    var progressBar: ProgressBar? = null
    var llContainer: LinearLayout? = null
    var imageError: ImageView? = null
    var tvErrorInfo: TextView? = null
    var btnRetry: TextView? = null

    val retryListener: RetryListener? = null

    init {
        init()
    }

    fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_load, this, true)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER


        progressBar = findViewById(R.id.progressBar) as ProgressBar
        llContainer = findViewById(R.id.llContainer) as LinearLayout
        imageError = findViewById(R.id.imageError) as ImageView
        tvErrorInfo = findViewById(R.id.tvErrorInfo) as TextView
        btnRetry = findViewById(R.id.btnRetry) as TextView

        btnRetry?.setOnClickListener {
            retryListener?.onRetry()
        }
    }

    fun showLoad() {
        if (llContainer?.isShown!!) {
            llContainer?.visibility = View.GONE
        }
        progressBar?.visibility = View.VISIBLE
    }

    fun showError(error: String) {
        if (progressBar?.isShown!!) {
            progressBar?.visibility = View.GONE
        }
        llContainer?.visibility = View.VISIBLE
        tvErrorInfo?.text = error
    }

    open interface RetryListener {
        fun onRetry()
    }
}
