package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.event.SettingModel
import com.github.jokar.zhihudaily.ui.activity.BaseActivity
import com.github.jokar.zhihudaily.ui.activity.SettingActivity
import com.github.jokar.zhihudaily.ui.view.SettingView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/7/5.
 */
@Module
class SettingPresenterModule {

    @Provides
    fun modelProvider(activity: SettingActivity): SettingModel {
        return SettingModel(activity)
    }

    @Provides
    fun viewProvider(activity: SettingActivity): SettingView {
        return activity
    }
}