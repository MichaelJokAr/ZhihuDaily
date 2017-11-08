package com.github.jokar.zhihudaily.model.event

import android.arch.lifecycle.Lifecycle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.github.jokar.zhihudaily.di.component.model.DaggerCollectionModelComponent
import com.github.jokar.zhihudaily.di.component.room.DaggerAppDatabaseComponent
import com.github.jokar.zhihudaily.di.module.room.AppDatabaseModule
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.network.result.ListResourceObserver
import com.github.jokar.zhihudaily.room.AppDatabaseHelper
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/4.
 */
class CollectionModel(var activity: AppCompatActivity) {

    @Inject
    lateinit var mDatabaseHelper: AppDatabaseHelper

    init {
        val component = DaggerAppDatabaseComponent.builder()
                .appDatabaseModule(AppDatabaseModule(activity.applicationContext))
                .build()

        DaggerCollectionModelComponent.builder()
                .appDatabaseComponent(component)
                .build()
                .inject(this)
    }

    /**
     * 获取所有收藏的story
     */
    fun getCollectionsStories(@NonNull callBack: ListDataCallBack<StoryEntity>) {

        Observable.create(ObservableOnSubscribe<ArrayList<StoryEntity>> { e ->
            var arrayList = mDatabaseHelper.getCollectionsStories()
            if (arrayList == null) {
                arrayList = arrayListOf()
            }
            e.onNext(arrayList)
        })
                .bindUntilEvent(activity, Lifecycle.Event.ON_DESTROY)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ListResourceObserver(callBack))
    }
}