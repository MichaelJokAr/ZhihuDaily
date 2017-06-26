package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.db.StoryDB
import com.github.jokar.zhihudaily.di.component.db.DaggerStoryDBComponent
import com.github.jokar.zhihudaily.di.component.network.DaggerNewsComponent
import com.github.jokar.zhihudaily.di.module.db.StoryDBModule
import com.github.jokar.zhihudaily.di.module.network.NewsModule
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.model.network.services.NewsServices
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailModel(var context: Context) {

    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var service: NewsServices
    @Inject
    lateinit var storyDB: StoryDB

    init {
        val storyDBComponent = DaggerStoryDBComponent.builder()
                .storyDBModule(StoryDBModule(context))
                .build()

        DaggerNewsComponent
                .builder()
                .storyDBComponent(storyDBComponent)
                .networkComponent(MyApplication.getNetComponent())
                .newsModule(NewsModule())
                .build()
                .inject(this)
    }

    fun getStoryDetail(id: Int,
                       @NonNull transformer: LifecycleTransformer<StoryDetail>,
                       @NonNull callBack: SingleDataCallBack<StoryDetail>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        service.getNews(id)
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { storyDetail ->
                    val state = storyDB.getLikeAndCollectionState(id)
                    storyDetail.like = state.first
                    storyDetail.collection = state.second
                    storyDetail
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    callBack.data(result)
                }, {
                    error ->
                    callBack.onError(error)
                }, {
                    callBack.onComplete()
                }, {
                    callBack.onStart()
                })
    }
}