package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent

/**
 * 首页
 * Created by JokAr on 2017/6/14.
 */
class SplashActivity : BaseActivity() {

    var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //某些手机release版本会在点击home键后再点击icon后会再走一遍app启动过程
        if (!isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }
        }
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 隐藏状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        createView()
    }

    fun createView() {
        coordinatorLayout {
            image = imageView {
                imageResource = R.mipmap.splash
                scaleType = ImageView.ScaleType.FIT_XY
            }.lparams(width = matchParent, height = matchParent)
        }
    }

    override fun getPresent(): BasePresenter? {
        return null
    }

    override fun onWindowInitialized() {
        super.onWindowInitialized()
        animation()
    }

    private fun animation() {
        //图片放大动画
        val animation = ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        animation.interpolator = DecelerateInterpolator()
        animation.duration = 1000 * 2
        animation.fillAfter = true

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                //动画结束进入MainActivity
                MainActivity.enter(this@SplashActivity)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        image?.startAnimation(animation)
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.clear(this, image)
        image = null
    }
}