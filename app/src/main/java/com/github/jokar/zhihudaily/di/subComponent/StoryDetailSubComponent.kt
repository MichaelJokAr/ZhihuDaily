package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.StoryDetailPresenterModule
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/6/25.
 */
@Subcomponent(modules = arrayOf(StoryDetailPresenterModule::class))
interface StoryDetailSubComponent:AndroidInjector<StoryDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<StoryDetailActivity>()
}