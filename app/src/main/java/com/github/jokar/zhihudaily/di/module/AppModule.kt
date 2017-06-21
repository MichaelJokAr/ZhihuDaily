package com.github.jokar.zhihudaily.di.module

import android.content.Context
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.subComponent.MainFragmentSubComponent
import com.github.jokar.zhihudaily.di.subComponent.MainSubComponent
import dagger.Module
import dagger.Provides


/**
 * Created by JokAr on 2017/6/15.
 */
@Module(subcomponents = arrayOf(MainSubComponent::class,
        MainFragmentSubComponent::class))
class AppModule {

    @Provides
    fun contextProvider(application: MyApplication): Context {
        return application.applicationContext
    }
}