package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.os.Bundle
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * Created by JokAr on 2017/7/4.
 */
class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initToolbar(toolbar, "设置")
        init()
    }

    fun init() {
        tvVersonName.text = "版本：${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"

        tvClear.setOnClickListener {

        }

        tvAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}