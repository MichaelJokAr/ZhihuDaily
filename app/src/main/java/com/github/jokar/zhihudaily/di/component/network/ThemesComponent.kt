package com.github.jokar.zhihudaily.di.component.network

import com.github.jokar.zhihudaily.di.module.network.ThemesModule
import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.event.MainModel
import dagger.Component

/**
 * Created by JokAr on 2017/6/15.
 */
@UserScope
@Component(dependencies = arrayOf(NetworkComponent::class),
        modules = arrayOf(ThemesModule::class))
interface ThemesComponent {
    fun inject(model: MainModel)
}