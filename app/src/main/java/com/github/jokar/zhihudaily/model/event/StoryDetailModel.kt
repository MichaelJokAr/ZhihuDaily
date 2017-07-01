package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.component.network.DaggerNewsComponent
import com.github.jokar.zhihudaily.di.component.room.DaggerAppDataBaseComponent
import com.github.jokar.zhihudaily.di.module.network.NewsModule
import com.github.jokar.zhihudaily.di.module.room.AppDataBaseModule
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.model.network.result.SingleResourceObserver
import com.github.jokar.zhihudaily.model.network.services.NewsServices
import com.github.jokar.zhihudaily.room.AppDataBaseHelper
import com.github.jokar.zhihudaily.utils.HtmlUtil
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    //room
    @Inject
    lateinit var dataBaseHelper: AppDataBaseHelper

    init {

        val component = DaggerAppDataBaseComponent.builder()
                .appDataBaseModule(AppDataBaseModule(context))
                .build()

        DaggerNewsComponent
                .builder()
                .networkComponent(MyApplication.getNetComponent())
                .appDataBaseComponent(component)
                .newsModule(NewsModule())
                .build()
                .inject(this)
    }

    fun getStoryDetail(id: Int,
                       @NonNull transformer: LifecycleTransformer<StoryDetail>,
                       @NonNull callBack: SingleDataCallBack<StoryEntity>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        Observable.create(ObservableOnSubscribe<StoryEntity> {
            e ->
            var story = dataBaseHelper.getStory(id)
            e.onNext(story)
        })
                .filter {
                    story ->
                    //判断本地是否有详细数据
                    if (!TextUtils.isEmpty(story?.body)) {
                        callBack.data(story!!)
                        callBack.onComplete()
                        return@filter false
                    }
                    return@filter true
                }
                //没有从网络获取
                .flatMap { service.getNews(id) }
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { (body, image_source, _, image, _, js, _, _, css) ->
                    //添加格式到body
                    var story = dataBaseHelper.getStory(id)
                    story.body = HtmlUtil.createHtmlData(css, js, body)
                    story.image_source = image_source
                    story.image = image
                    //更新本地数据
                    dataBaseHelper.updateStory(story)
                    //传递story
                    story
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SingleResourceObserver(callBack))
    }


    /**
     * 更新story
     */
    fun updateStory(story: StoryEntity, transformer: LifecycleTransformer<Any>) {
        Observable.just(story)
                .compose(transformer)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .subscribe {

                    dataBaseHelper.updateStory(story)
                }
    }
}