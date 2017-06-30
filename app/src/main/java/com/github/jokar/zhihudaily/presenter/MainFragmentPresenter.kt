package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.MainFragmentModel
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/21.
 */
class MainFragmentPresenter @Inject constructor(var model: MainFragmentModel?,
                                                var view: StoryView?) : BasePresenter {

    fun getLatestStory(@NonNull transformer: LifecycleTransformer<LatestStory>) {
        model?.getLatestStory(transformer,
                object : SingleDataCallBack<LatestStory> {
                    override fun data(data: LatestStory) {
                        view?.loadData(data)
                        view?.loadComplete()
                    }

                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view?.fail(e)
                    }
                })
    }

    fun getNextDayStory(date: Long,
                        @NonNull transformer: LifecycleTransformer<LatestStory>) {

        model?.getBeforeStory(date, transformer,
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
    fun updateStory(story: StoryEntity,
                    @NonNull transformer: LifecycleTransformer<Any>){
        model?.updateStory(story,transformer)
    }
    override fun destroy() {
        model = null
        view = null
    }
}