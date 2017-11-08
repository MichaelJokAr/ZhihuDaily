package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.MainFragmentModel
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.presenter.base.SingleDataViewCallBack
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/21.
 */
class MainFragmentPresenter @Inject constructor(var model: MainFragmentModel?,
                                                var view: StoryView?) : BasePresenter {

    fun getLatestStory() {
        model?.getLatestStory( SingleDataViewCallBack(view))
    }

    fun getNextDayStory(date: Long) {

        model?.getBeforeStory(date,
                object : ListDataCallBack<StoryEntity> {

                    override fun data(data: ArrayList<StoryEntity>) {
                        view?.loadMoreData(data)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view?.loadMoreFail(e)
                    }

                })
    }

    /**
     * 更新已读
     */
    fun updateStory(story: StoryEntity) {
        model?.updateStory(story)
    }

    override fun destroy() {
        model = null
        view = null
    }
}