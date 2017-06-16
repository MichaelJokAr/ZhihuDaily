package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.event.MainModel
import com.github.jokar.zhihudaily.ui.activity.MainActivity
import com.github.jokar.zhihudaily.ui.view.MainView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/16.
 */
@Module
class MainPresenterModule {

    @Provides
    fun provider(): MainModel {
        return MainModel()
    }

    @Provides
    fun viewProvider(activity: MainActivity): MainView {
        return activity
    }
}