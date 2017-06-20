package com.github.jokar.zhihudaily.app

import android.app.Activity
import android.app.Application
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.di.component.DaggerAppComponent
import com.github.jokar.zhihudaily.di.component.db.DaggerStoryDBComponent
import com.github.jokar.zhihudaily.di.component.db.StoryDBComponent
import com.github.jokar.zhihudaily.di.component.network.DaggerNetworkComponent
import com.github.jokar.zhihudaily.di.component.network.NetworkComponent
import com.github.jokar.zhihudaily.di.module.NetworkModule
import com.github.jokar.zhihudaily.di.module.db.StoryDBModule
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/14.
 */
class MyApplication : Application(), HasActivityInjector {
    var NETCOMPONENT: NetworkComponent? = null
    var STORYDBCOMPONENT: StoryDBComponent? = null

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    companion object {
        @Volatile
        var INSTANCE: MyApplication? = null

        fun getInstance(): MyApplication {
            if (INSTANCE == null) {
                synchronized(MyApplication::class) {
                    if (INSTANCE == null) {
                        INSTANCE = MyApplication()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        DaggerAppComponent.create()
                .inject(this)

        Thread {
            //初始化xLog
            XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
        }.start()

    }

    fun getNetComponent(): NetworkComponent {
        if (NETCOMPONENT == null) {
            NETCOMPONENT = DaggerNetworkComponent
                    .builder()
                    .networkModule(NetworkModule())
                    .build()
        }
        return NETCOMPONENT!!
    }

    fun getStoryDBComponent(): StoryDBComponent {
        if (STORYDBCOMPONENT == null) {
            STORYDBCOMPONENT = DaggerStoryDBComponent
                    .builder()
                    .storyDBModule(StoryDBModule(applicationContext))
                    .build()
        }
        return STORYDBCOMPONENT!!
    }
}