package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import com.github.jokar.zhihudaily.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 首页
 * Created by JokAr on 2017/6/14.
 */
class SplashActivity : BaseActivity() {

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
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
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
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        image.startAnimation(animation)
    }
}