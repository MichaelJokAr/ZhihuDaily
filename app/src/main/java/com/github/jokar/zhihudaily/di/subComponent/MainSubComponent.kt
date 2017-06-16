package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.MainPresenterModule
import com.github.jokar.zhihudaily.ui.activity.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/6/16.
 */
@Subcomponent(modules = arrayOf(MainPresenterModule::class))
interface MainSubComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}