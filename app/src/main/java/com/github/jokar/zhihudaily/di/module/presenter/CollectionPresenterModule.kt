package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.CollectionModel
import com.github.jokar.zhihudaily.ui.activity.CollectionActivity
import com.github.jokar.zhihudaily.ui.view.common.ListDataView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/7/4.
 */
@Module
class CollectionPresenterModule {

    @Provides
    fun modelProvider(activity: CollectionActivity): CollectionModel {
        return CollectionModel(activity)
    }

    @Provides
    fun viewProvider(activity: CollectionActivity): ListDataView<StoryEntity> {
        return activity
    }
}