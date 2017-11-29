package com.github.jokar.zhihudaily.app

import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.di.component.DaggerAppComponent
import com.github.jokar.zhihudaily.di.component.network.DaggerNetworkComponent
import com.github.jokar.zhihudaily.di.component.network.NetworkComponent
import com.github.jokar.zhihudaily.di.module.NetworkModule
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication

/**
 * Created by JokAr on 2017/6/14.
 */
class MyApplication : DaggerApplication(), HasActivityInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()

    companion object {
        private var NETCOMPONENT: NetworkComponent? = null

        fun getNetComponent(): NetworkComponent {
            if (NETCOMPONENT == null) {
                NETCOMPONENT = DaggerNetworkComponent
                        .builder()
                        .networkModule(NetworkModule())
                        .build()
            }
            return NETCOMPONENT!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        //初始化xLog
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)

    }


}