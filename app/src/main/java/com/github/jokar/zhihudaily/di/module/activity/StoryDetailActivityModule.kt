package com.github.jokar.zhihudaily.di.module.activity

import android.app.Activity
import com.github.jokar.zhihudaily.di.subComponent.StoryDetailSubComponent
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/6/25.
 */
@Module
abstract class StoryDetailActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(StoryDetailActivity::class)
    internal abstract fun bind(builder: StoryDetailSubComponent.Builder): AndroidInjector.Factory<out Activity>
}