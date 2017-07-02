package com.github.jokar.zhihudaily.di.module.fragment

import android.support.v4.app.Fragment
import com.github.jokar.zhihudaily.di.subComponent.ThemeFragmentSubComponent
import com.github.jokar.zhihudaily.ui.fragment.ThemeFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by JokAr on 2017/7/2.
 */
@Module
abstract class ThemeFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(ThemeFragment::class)
    internal abstract fun bind(builder: ThemeFragmentSubComponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}