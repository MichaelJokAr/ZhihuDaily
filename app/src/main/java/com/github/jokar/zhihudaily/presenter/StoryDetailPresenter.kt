package com.github.jokar.zhihudaily.presenter

import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.model.event.StoryDetailModel
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailPresenter @Inject constructor(var model: StoryDetailModel?,
                                               var view: SingleDataView<StoryDetail>?)
    : BasePresenter {

    fun getStoryDetail(id:Int,transformer: LifecycleTransformer<StoryDetail>){
        model?.getStoryDetail(id,transformer,
                object :SingleDataCallBack<StoryDetail>{
                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }
                    override fun data(data: StoryDetail) {
                        view?.loadData(data)
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view?.fail(e)
                    }
                    override fun onComplete() {
                        super.onComplete()
                        view?.loadComplete()
                    }
                })
    }

    override fun destroy() {
        model = null
        view = null
    }
}