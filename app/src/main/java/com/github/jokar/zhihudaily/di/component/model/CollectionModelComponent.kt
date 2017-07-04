package com.github.jokar.zhihudaily.di.component.model

import com.github.jokar.zhihudaily.di.component.room.AppDatabaseComponent
import com.github.jokar.zhihudaily.model.event.CollectionModel
import dagger.Component

/**
 * Created by JokAr on 2017/7/4.
 */
@Component(dependencies = arrayOf(AppDatabaseComponent::class))
interface CollectionModelComponent {
    fun inject(model: CollectionModel)
}