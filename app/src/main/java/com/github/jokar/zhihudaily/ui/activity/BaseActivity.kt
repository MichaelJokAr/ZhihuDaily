package com.github.jokar.zhihudaily.ui.activity

import android.graphics.Color
import android.support.v7.widget.Toolbar
import com.github.jokar.zhihudaily.utils.system.JLog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by JokAr on 2017/6/14.
 */
abstract class BaseActivity : RxAppCompatActivity() {
    private var isFirstFocused = true

    fun initToolbar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { v -> finish() }
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
}