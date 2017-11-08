package com.github.jokar.zhihudaily.presenter

import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.StoryDetailModel
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.presenter.base.SingleDataViewCallBack
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailPresenter @Inject constructor(var model: StoryDetailModel?,
                                               var view: SingleDataView<StoryEntity>?)
    : BasePresenter {

    fun getStoryDetail(id: Int) {
        model?.getStoryDetail(id, SingleDataViewCallBack(view))
    }

    /**
     * 更新story
     */
    fun updateStory(story: StoryEntity) {
        model?.updateStory(story)
    }

    override fun destroy() {
        model = null
        view = null
    }
}