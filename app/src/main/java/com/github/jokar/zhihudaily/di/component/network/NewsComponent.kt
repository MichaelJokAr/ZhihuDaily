package com.github.jokar.zhihudaily.di.component.network

import com.github.jokar.zhihudaily.di.component.room.AppDataBaseComponent
import com.github.jokar.zhihudaily.di.module.network.NewsModule
import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.event.StoryDetailModel
import dagger.Component

/**
 * Created by JokAr on 2017/6/25.
 */
@UserScope
@Component(dependencies = arrayOf(NetworkComponent::class,
        AppDataBaseComponent::class),
        modules = arrayOf(NewsModule::class))
interface NewsComponent {
    fun inject(model: StoryDetailModel)
}