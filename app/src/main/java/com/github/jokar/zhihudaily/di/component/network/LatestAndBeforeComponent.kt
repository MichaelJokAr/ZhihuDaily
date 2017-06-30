package com.github.jokar.zhihudaily.di.component.network

import com.github.jokar.zhihudaily.di.component.room.AppDataBaseComponent
import com.github.jokar.zhihudaily.di.module.network.BeforeModule
import com.github.jokar.zhihudaily.di.module.network.LatestModule
import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.event.MainFragmentModel
import dagger.Component

/**
 * Created by JokAr on 2017/6/20.
 */
@UserScope
@Component(dependencies = arrayOf(NetworkComponent::class,
        AppDataBaseComponent::class),
        modules = arrayOf(LatestModule::class, BeforeModule::class))
interface LatestAndBeforeComponent {
    fun inject(model: MainFragmentModel)
}