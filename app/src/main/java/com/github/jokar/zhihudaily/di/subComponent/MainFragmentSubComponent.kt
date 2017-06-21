package com.github.jokar.zhihudaily.di.subComponent

import com.github.jokar.zhihudaily.di.module.presenter.MainFragmentPresenterModule
import com.github.jokar.zhihudaily.ui.fragment.MainFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by JokAr on 2017/6/21.
 */
@Subcomponent(modules = arrayOf(MainFragmentPresenterModule::class))
interface MainFragmentSubComponent : AndroidInjector<MainFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainFragment>()
}