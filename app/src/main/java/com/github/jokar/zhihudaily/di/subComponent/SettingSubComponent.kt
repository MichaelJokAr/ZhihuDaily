package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.SettingPresenterModule
import com.github.jokar.zhihudaily.ui.activity.SettingActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/7/5.
 */
@Subcomponent(modules = arrayOf(SettingPresenterModule::class))
interface SettingSubComponent: AndroidInjector<SettingActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SettingActivity>()
}