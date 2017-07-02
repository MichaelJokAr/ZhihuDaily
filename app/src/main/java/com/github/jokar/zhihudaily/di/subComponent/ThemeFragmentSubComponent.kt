package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.ThemePresenterModule
import com.github.jokar.zhihudaily.ui.fragment.ThemeFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/7/2.
 */
@Subcomponent(modules = arrayOf(ThemePresenterModule::class))
interface ThemeFragmentSubComponent : AndroidInjector<ThemeFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ThemeFragment>()
}