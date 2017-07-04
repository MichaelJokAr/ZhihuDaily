package com.github.jokar.zhihudaily.di.module.activity

import android.app.Activity
import com.github.jokar.zhihudaily.di.subComponent.CollectionSubComponent
import com.github.jokar.zhihudaily.ui.activity.CollectionActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/7/4.
 */
@Module
abstract class CollectionActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(CollectionActivity::class)
    internal abstract fun bind(builder: CollectionSubComponent.Builder): AndroidInjector.Factory<out Activity>
}