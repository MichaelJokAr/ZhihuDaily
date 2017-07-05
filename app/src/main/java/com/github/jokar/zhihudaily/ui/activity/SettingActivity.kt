package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.os.Bundle
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.presenter.SettingPresenter
import com.github.jokar.zhihudaily.ui.view.SettingView
import com.github.jokar.zhihudaily.utils.system.JToast
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.common_toolbar.*
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/4.
 */
class SettingActivity : BaseActivity(), SettingView {

    @Inject
    lateinit var presenter: SettingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initToolbar(toolbar, "设置")
        init()
    }

    fun init() {
        tvVersonName.text = "版本：${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"

        tvClear.setOnClickListener {
            presenter.clearImageCache(bindUntilEvent(ActivityEvent.DESTROY))
        }

        tvAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    override fun onWindowInitialized() {
        super.onWindowInitialized()
        presenter.getImageCacheSize(bindUntilEvent(ActivityEvent.DESTROY))
    }
    /**
     * 显示图片缓存
     */
    override fun showImageCacheSize(size: String) {
        tvClear.text = "清除缓存($size)"
    }

    /**
     * 显示清除缓存成功
     */
    override fun showClearSuccess() {
        JToast.toast(this, "清除缓存成功")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}