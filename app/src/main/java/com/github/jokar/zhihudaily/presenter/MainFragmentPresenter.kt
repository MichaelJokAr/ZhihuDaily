package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.model.event.MainFragmentModel
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.LoadMoreView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/21.
 */
class MainFragmentPresenter @Inject constructor(var model: MainFragmentModel?,
                                                var view: LoadMoreView<StoryEntities>?) : BasePresenter {

    fun getLatestStory(@NonNull transformer: LifecycleTransformer<LatestStory>){
        model?.getLatestStory(transformer,
                object :ListDataCallBack<StoryEntities>{
                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }
                    override fun data(data: ArrayList<StoryEntities>) {
                        view?.loadData(data)
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

    fun getNextDayStory(){

    }

    override fun destroy() {
        model = null
        view = null
    }
}