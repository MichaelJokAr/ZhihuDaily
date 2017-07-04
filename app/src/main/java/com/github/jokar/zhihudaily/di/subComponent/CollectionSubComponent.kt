package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.CollectionPresenterModule
import com.github.jokar.zhihudaily.ui.activity.CollectionActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/7/4.
 */
@Subcomponent(modules = arrayOf(CollectionPresenterModule::class))
interface CollectionSubComponent : AndroidInjector<CollectionActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CollectionActivity>()
}