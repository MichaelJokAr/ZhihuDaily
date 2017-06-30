package com.github.jokar.zhihudaily.presenter

import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.StoryDetailModel
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.system.JLog
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailPresenter @Inject constructor(var model: StoryDetailModel?,
                                               var view: SingleDataView<StoryEntity>?)
    : BasePresenter {

    fun getStoryDetail(id:Int,transformer: LifecycleTransformer<StoryDetail>){
        model?.getStoryDetail(id,transformer,
                object :SingleDataCallBack<StoryEntity>{
                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }
                    override fun data(data: StoryEntity) {
                        view?.loadData(data)
                        view?.loadComplete()
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        JLog.e(e)
                        view?.fail(e)
                    }
                })
    }

    override fun destroy() {
        model = null
        view = null
    }
}