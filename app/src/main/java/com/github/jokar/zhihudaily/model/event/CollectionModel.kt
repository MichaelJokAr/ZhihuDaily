package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.di.component.model.DaggerCollectionModelComponent
import com.github.jokar.zhihudaily.di.component.room.DaggerAppDatabaseComponent
import com.github.jokar.zhihudaily.di.module.room.AppDatabaseModule
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.network.result.ListResourceObserver
import com.github.jokar.zhihudaily.room.AppDatabaseHelper
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/4.
 */
class CollectionModel(var context: Context) {

    @Inject
    lateinit var mDatabaseHelper: AppDatabaseHelper

    init {
        val component = DaggerAppDatabaseComponent.builder()
                .appDatabaseModule(AppDatabaseModule(context))
                .build()

        DaggerCollectionModelComponent.builder()
                .appDatabaseComponent(component)
                .build()
                .inject(this)
    }

    /**
     * 获取所有收藏的story
     */
    fun getCollectionsStories(@NonNull transformer: LifecycleTransformer<ArrayList<StoryEntity>>,
                              @NonNull callBack: ListDataCallBack<StoryEntity>) {

        Observable.create(ObservableOnSubscribe<ArrayList<StoryEntity>> {
            e ->
            var arrayList = mDatabaseHelper.getCollectionsStories()
            if (arrayList == null) {
                arrayList = arrayListOf()
            }
            e.onNext(arrayList)
        })
                .compose(transformer)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ListResourceObserver(callBack))
    }
}