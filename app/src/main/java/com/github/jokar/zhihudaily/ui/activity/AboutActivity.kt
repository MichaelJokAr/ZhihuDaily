package com.github.jokar.zhihudaily.ui.activity

import android.os.Bundle
import android.view.View
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.common_toolbar.*
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

/**
 * Created by JokAr on 2017/7/4.
 */
class AboutActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initToolbar(toolbar,"关于")

        linearLayout.addView(getPage())
    }

    fun getPage(): View {
        return AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)//图片
                .setDescription("没天三次，每次七分钟")//介绍
                .addGroup("与我联系")
                .addEmail("1018875550@qq.com","联系我")//邮箱
                .addWebsite("http://www.jianshu.com/p/1114c471281b","关于此项目介绍")//网站
                .addGitHub("a1018875550")//github
                .create()
    }
}