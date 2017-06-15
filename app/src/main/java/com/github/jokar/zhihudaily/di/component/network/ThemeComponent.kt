package com.github.jokar.zhihudaily.di.component.network

import com.github.jokar.zhihudaily.di.module.network.ThemeModule
import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.event.MainModel
import dagger.Component

/**
 * Created by JokAr on 2017/6/15.
 */
@UserScope
@Component(dependencies = arrayOf(NetworkComponent::class), modules = arrayOf(ThemeModule::class))
interface ThemeComponent {
    fun inject(model: MainModel)
}