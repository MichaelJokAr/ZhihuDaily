package com.github.jokar.zhihudaily.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.afollestad.aesthetic.Aesthetic
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.utils.system.JLog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by JokAr on 2017/6/14.
 */
abstract class BaseActivity : RxAppCompatActivity() {
    private var isFirstFocused = true

    abstract fun getPresent():BasePresenter?

    fun initToolbar(toolbar: Toolbar?, title: String) {
        toolbar?.title = title
        toolbar?.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { _ -> finish() }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isFirstFocused && hasFocus) {
            isFirstFocused = false
            onWindowInitialized()
        }
    }

    open fun onWindowInitialized() {
        JLog.d("onWindowInitialized")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //change theme
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        //change theme
        Aesthetic.resume(this)
        super.onResume()
    }

    override fun onPause() {
        //change theme
        Aesthetic.pause(this)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresent()?.destroy()
    }
}