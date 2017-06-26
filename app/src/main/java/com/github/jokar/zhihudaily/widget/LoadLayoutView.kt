package com.github.jokar.zhihudaily.widget

import android.animation.LayoutTransition
import android.content.Context
import android.os.Build
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
 * Created by JokAr on 2017/6/25.
 */
class LoadLayoutView : LinearLayout {
    var progressBar: ProgressBar? = null
    var llContainer: LinearLayout? = null
    var imageError: ImageView? = null
    var tvErrorInfo: TextView? = null
    var btnRetry: TextView? = null

    var retryListener:RetryListener? = null
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_load, this, true)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            layoutTransition = LayoutTransition()
        }

        progressBar = findViewById(R.id.progressBar) as ProgressBar?
        llContainer = findViewById(R.id.llContainer) as LinearLayout?
        imageError = findViewById(R.id.imageError) as ImageView?
        tvErrorInfo = findViewById(R.id.tvErrorInfo) as TextView?
        btnRetry = findViewById(R.id.btnRetry) as TextView?

        btnRetry?.setOnClickListener {
            retryListener?.onRetry()
        }
    }

    fun showLoad(){
        if(llContainer?.isShown as Boolean){
            llContainer?.visibility = View.GONE
        }
        progressBar?.visibility = View.VISIBLE
    }

    fun showError(error:String){
        if(progressBar?.isShown as Boolean){
            progressBar?.visibility = View.GONE
        }
        llContainer?.visibility = View.VISIBLE
        tvErrorInfo?.text = error
    }

    fun hidRetryButton(){

    }


    interface RetryListener{
        fun onRetry()
    }
}