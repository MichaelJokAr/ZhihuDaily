package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.CollectionModel
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.presenter.base.ListDataViewCallBack
import com.github.jokar.zhihudaily.ui.view.common.ListDataView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/4.
 */
class CollectionPresenter @Inject constructor(var model: CollectionModel?,
                                              var view: ListDataView<StoryEntity>?) : BasePresenter {


    fun getCollections(@NonNull transformer: LifecycleTransformer<ArrayList<StoryEntity>>) {

        model?.getCollectionsStories(transformer, ListDataViewCallBack(view))
    }

    override fun destroy() {
        model = null
        view = null
    }
}