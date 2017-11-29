package com.github.jokar.zhihudaily.di.component

import android.app.Application
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.module.AppModule
import com.github.jokar.zhihudaily.di.module.activity.ActivityModule
import com.github.jokar.zhihudaily.di.module.activity.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


/**
 * Created by JokAr on 2017/6/15.
 */
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        FragmentModule::class,
        ActivityModule::class
))
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    override fun inject(application: MyApplication)

}