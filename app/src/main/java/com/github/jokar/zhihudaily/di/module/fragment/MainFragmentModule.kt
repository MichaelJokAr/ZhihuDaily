package com.github.jokar.zhihudaily.di.module.fragment

import android.support.v4.app.Fragment
import com.github.jokar.zhihudaily.di.subComponent.MainFragmentSubComponent
import com.github.jokar.zhihudaily.ui.fragment.MainFragment
import dagger.Binds
import dagger.Module
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import dagger.android.AndroidInjector



/**
 * Created by JokAr on 2017/6/21.
 */
@Module
abstract class MainFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    internal abstract fun bind(builder: MainFragmentSubComponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}