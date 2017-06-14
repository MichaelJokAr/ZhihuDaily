package com.github.jokar.zhihudaily.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by JokAr on 2017/6/14.
 */
class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}