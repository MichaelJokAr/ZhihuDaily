package com.github.jokar.zhihudaily.di.module.activity

import android.app.Activity
import com.github.jokar.zhihudaily.di.subComponent.SettingSubComponent
import com.github.jokar.zhihudaily.ui.activity.SettingActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/7/5.
 */
@Module
abstract class SettingActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(SettingActivity::class)
    internal abstract fun bind(builder: SettingSubComponent.Builder): AndroidInjector.Factory<out Activity>
}