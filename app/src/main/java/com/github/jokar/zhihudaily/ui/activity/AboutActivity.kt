package com.github.jokar.zhihudaily.ui.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.View
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import kotlinx.android.synthetic.main.common_toolbar.*
import mehdi.sakout.aboutpage.AboutPage
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.include
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

/**
 * Created by JokAr on 2017/7/4.
 */
class AboutActivity : BaseActivity() {
    var linearLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createView()
        initToolbar(toolbar, "关于")

        linearLayout?.addView(getPage())
    }


    fun createView() {
        coordinatorLayout {
            include<View>(R.layout.common_toolbar)
            linearLayout = linearLayout {
                orientation = LinearLayout.VERTICAL
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }

    fun getPage(): View {
        return AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)//图片
                .setDescription("没天三次，每次七分钟")//介绍
                .addGroup("与我联系")
                .addEmail("1018875550@qq.com", "联系我")//邮箱
                .addWebsite("https://fir.im/w4zk", "检测更新")//网站
                .addWebsite("http://www.jianshu.com/p/1114c471281b", "关于此项目介绍")//网站
                .addGitHub("a1018875550")//github
                .create()
    }

    override fun onDestroy() {
        super.onDestroy()
        linearLayout = null
    }
}