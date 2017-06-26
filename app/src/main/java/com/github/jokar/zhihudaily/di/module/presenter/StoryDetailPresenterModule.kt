package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.model.event.StoryDetailModel
import com.github.jokar.zhihudaily.ui.activity.StoryDetailActivity
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/25.
 */
@Module
class StoryDetailPresenterModule {

    @Provides
    fun modelProvider(activity:StoryDetailActivity):StoryDetailModel{
        return StoryDetailModel(activity.applicationContext)
    }

    @Provides
    fun viewProvider(activity: StoryDetailActivity):SingleDataView<StoryDetail>{
        return activity
    }
}