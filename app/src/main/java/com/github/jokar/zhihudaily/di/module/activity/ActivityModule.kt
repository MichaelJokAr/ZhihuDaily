package com.github.jokar.zhihudaily.di.module.activity

import android.app.Activity
import com.github.jokar.zhihudaily.di.subComponent.CollectionSubComponent
import com.github.jokar.zhihudaily.di.subComponent.MainSubComponent
import com.github.jokar.zhihudaily.di.subComponent.SettingSubComponent
import com.github.jokar.zhihudaily.di.subComponent.StoryDetailSubComponent
import com.github.jokar.zhihudaily.ui.activity.CollectionActivity
import com.github.jokar.zhihudaily.ui.activity.MainActivity
import com.github.jokar.zhihudaily.ui.activity.SettingActivity
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/11/29.
 */
@Module
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindMainActivity(builder: MainSubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(CollectionActivity::class)
    internal abstract fun bindCollectionActivity(builder: CollectionSubComponent.Builder): AndroidInjector.Factory<out Activity>


    @Binds
    @IntoMap
    @ActivityKey(SettingActivity::class)
    internal abstract fun bindSettingActivity(builder: SettingSubComponent.Builder): AndroidInjector.Factory<out Activity>


    @Binds
    @IntoMap
    @ActivityKey(StoryDetailActivity::class)
    internal abstract fun bindStoryDetailActivity(builder: StoryDetailSubComponent.Builder): AndroidInjector.Factory<out Activity>
}