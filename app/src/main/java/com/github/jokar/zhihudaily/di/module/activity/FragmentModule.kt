package com.github.jokar.zhihudaily.di.module.activity

import android.support.v4.app.Fragment
import com.github.jokar.zhihudaily.di.subComponent.MainFragmentSubComponent
import com.github.jokar.zhihudaily.di.subComponent.ThemeFragmentSubComponent
import com.github.jokar.zhihudaily.ui.fragment.MainFragment
import com.github.jokar.zhihudaily.ui.fragment.ThemeFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/11/29.
 */
@Module
abstract class FragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    internal abstract fun bindMainFragment(builder: MainFragmentSubComponent.Builder)
            : AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ThemeFragment::class)
    internal abstract fun bindThemeFragment(builder: ThemeFragmentSubComponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}