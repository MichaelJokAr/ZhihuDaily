package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
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
                    }

                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }

                    override fun onComplete() {
                        super.onComplete()
                        view?.loadComplete()
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
                object : ListDataCallBack<StoryEntities> {

                    override fun data(data: ArrayList<StoryEntities>) {
                        view?.loadMoreData(data)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view?.loadMoreFail(e)
                    }

                })
    }

    override fun destroy() {
        model = null
        view = null
    }
}