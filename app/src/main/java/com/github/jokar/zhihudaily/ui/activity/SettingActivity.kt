package com.github.jokar.zhihudaily.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.presenter.SettingPresenter
import com.github.jokar.zhihudaily.ui.layout.CommonView
import com.github.jokar.zhihudaily.ui.view.SettingView
import com.github.jokar.zhihudaily.utils.system.JToast
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.common_toolbar.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/4.
 */
class SettingActivity : BaseActivity(), SettingView {

    @Inject
    lateinit var presenter: SettingPresenter
    var tvClear: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        createView()
        initToolbar(toolbar, "设置")
    }

    private fun createView() {

        coordinatorLayout {
            include<View>(R.layout.common_toolbar)
            linearLayout {
                orientation = LinearLayout.VERTICAL
                //缓存
                tvClear = textView {
                    backgroundResource = CommonView.selectableItemBackground(this@SettingActivity)
                    text = "清除缓存"
                    textSize = 15f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(18), 0, dip(18), 0)
                    onClick {
                        presenter.clearImageCache(bindUntilEvent(ActivityEvent.DESTROY))
                    }
                }.lparams(width = matchParent, height = dip(48))

                view {
                    backgroundColor = Color.parseColor("#e0e0e0")
                }.lparams(width = matchParent, height = dip(1))

                //版本
                textView {
                    text = "版本：${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
                    textSize = 15f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(18), 0, dip(18), 0)
                }.lparams(width = matchParent, height = dip(48))

                view {
                    backgroundColor = Color.parseColor("#e0e0e0")
                }.lparams(width = matchParent, height = dip(1))

                //关于
                textView {
                    backgroundResource = CommonView.selectableItemBackground(this@SettingActivity)
                    text = "关于"
                    textSize = 15f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(18), 0, dip(18), 0)
                    onClick {
                        //跳转关于页面
                        startActivity(Intent(this@SettingActivity, AboutActivity::class.java))
                    }
                }.lparams(width = matchParent, height = dip(48))

                view {
                    backgroundColor = Color.parseColor("#e0e0e0")
                }.lparams(width = matchParent, height = dip(1))

                //关于
                textView {
                    backgroundResource = CommonView.selectableItemBackground(this@SettingActivity)
                    text = "变色龙"
                    textSize = 15f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(18), 0, dip(18), 0)
                    onClick {
                        //跳转变色龙页面
                        startActivity(Intent(this@SettingActivity, ChameleonActivity::class.java))
                    }
                }.lparams(width = matchParent, height = dip(48))

            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
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
        tvClear?.text = "清除缓存($size)"
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
        tvClear?.setOnClickListener { null }
        tvClear = null
    }
}