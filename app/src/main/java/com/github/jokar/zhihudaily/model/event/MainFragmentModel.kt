package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.component.network.DaggerLatestAndBeforeComponent
import com.github.jokar.zhihudaily.di.component.room.DaggerAppDatabaseComponent
import com.github.jokar.zhihudaily.di.module.network.BeforeModule
import com.github.jokar.zhihudaily.di.module.network.LatestModule
import com.github.jokar.zhihudaily.di.module.room.AppDatabaseModule
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.model.network.result.ListResourceObserver
import com.github.jokar.zhihudaily.model.network.result.SingleResourceObserver
import com.github.jokar.zhihudaily.model.network.services.BeforeService
import com.github.jokar.zhihudaily.model.network.services.LatestService
import com.github.jokar.zhihudaily.room.AppDatabaseHelper
import com.github.jokar.zhihudaily.utils.system.DateUtils
import com.github.jokar.zhihudaily.utils.system.JLog
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by JokAr on 2017/6/20.
 */
class MainFragmentModel(var context: Context) {

    val TODAYSNEWS = "今日热闻"
    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var latestService: LatestService

    @Inject
    lateinit var beforeService: BeforeService

    //room
    @Inject
    lateinit var mDatabaseHelper: AppDatabaseHelper

    init {
        val component = DaggerAppDatabaseComponent.builder()
                .appDatabaseModule(AppDatabaseModule(context))
                .build()

        DaggerLatestAndBeforeComponent.builder()
                .networkComponent(MyApplication.getNetComponent())
                .appDatabaseComponent(component)
                .latestModule(LatestModule())
                .beforeModule(BeforeModule())
                .build()
                .inject(this)
    }

    /**
     * 获取最新story
     */
    fun getLatestStory(@NonNull transformer: LifecycleTransformer<LatestStory>,
                       @NonNull callBack: SingleDataCallBack<LatestStory>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var date: Long = 0
        date = year * 10000L
        month *= 100
        date += month
        date += day
        JLog.w("date: $date")

        calendar = null

        Observable.create(ObservableOnSubscribe<LatestStory> { e ->

            //先检测本地是否有
            var latestStory: LatestStory = LatestStory(date, null, null)
            var stories: ArrayList<StoryEntity>? = mDatabaseHelper.getStoryByDate(date)
            var topStories: ArrayList<TopStoryEntity>? = mDatabaseHelper.getTopStoryByDate(date)

            //本地有就直接返回本地数据
            if (stories != null && stories?.size > 0) {

                //添加时间标题
                var timeTitle: StoryEntity = StoryEntity(0)
                timeTitle.date = date
                timeTitle.dateString = TODAYSNEWS
                stories.add(0, timeTitle)

                //添加head
                var head: StoryEntity = StoryEntity(-1)
                head.dateString = context.getString(R.string.app_name)
                stories.add(0, head)
                latestStory.stories = stories
            }

            if (topStories != null && topStories.size > 0) {
                latestStory.top_stories = topStories
            }

            e.onNext(latestStory)


        })
                .filter { t ->
                    if (t.stories != null && t.top_stories != null) {
                        t.stories?.forEach {
                            it.dateString = TODAYSNEWS
                        }
                        callBack.data(t)
                        callBack.onComplete()
                        return@filter false
                    }
                    return@filter true
                }
                .flatMap {
                    latestService.getStories()
                }
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { latestStory ->
                    //遍历，赋值时间
                    latestStory.stories?.forEach({
                        it.date = date
                        it.dateString = DateUtils.judgmentTime(date)
                    })
                    latestStory.top_stories?.forEach({
                        it.date = date
                    })

                    //保存到数据表
                    mDatabaseHelper.insertTopStory(latestStory.top_stories!!)
                    mDatabaseHelper.insertStory(latestStory.stories!!)

                    //重新设置dataString
                    latestStory.stories?.forEach({
                        it.dateString = TODAYSNEWS
                    })

                    //添加时间标题
                    var timeTitle: StoryEntity = StoryEntity(0)
                    timeTitle.date = date
                    timeTitle.dateString = TODAYSNEWS
                    latestStory.stories?.add(0, timeTitle)

                    //添加head
                    var head: StoryEntity = StoryEntity(-1)
                    head.dateString = context.getString(R.string.app_name)
                    latestStory.stories?.add(0, head)

                    latestStory
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SingleResourceObserver(callBack))
    }

    /**
     * 获取过往的story
     */
    fun getBeforeStory(date: Long,
                       @NonNull transformer: LifecycleTransformer<LatestStory>,
                       @NonNull callBack: ListDataCallBack<StoryEntity>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        Observable.create(ObservableOnSubscribe<ArrayList<StoryEntity>> { e ->
            //先检测本地是否有
            var beforeDate = DateUtils.getBeforeDate(date)

            val stories: ArrayList<StoryEntity>? = mDatabaseHelper.getStoryByDate(beforeDate)
            //本地有就直接返回本地数据
            if (stories != null && stories?.size > 0) {

                //添加时间标题
                var timeTitle: StoryEntity = StoryEntity(0)
                timeTitle.date = beforeDate
                timeTitle.dateString = DateUtils.judgmentTime(beforeDate)
                stories.add(0, timeTitle)
                e?.onNext(stories)
                e?.onComplete()
            } else {
                //本地没有再请求网络
                e?.onNext(ArrayList())
            }
        })
                .filter { t ->
                    if (t != null && t.size > 0) {
                        callBack.data(t)
                        callBack.onComplete()
                        return@filter false
                    }
                    return@filter true
                }
                .flatMap {
                    beforeService.getStories(date)
                }
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { (date, stories) ->
                    //遍历，赋值时间
                    stories?.forEach({
                        it.date = date
                        it.dateString = DateUtils.judgmentTime(date)
                    })

                    //保存到数据表
                    mDatabaseHelper.insertStory(stories!!)

                    //添加时间标题
                    var timeTitle: StoryEntity = StoryEntity(0)
                    timeTitle.date = date
                    timeTitle.dateString = DateUtils.judgmentTime(date)
                    stories?.add(0, timeTitle)
                    stories!!
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ListResourceObserver(callBack))
    }


    /**
     * 更新为已读
     */
    fun updateStory(story:StoryEntity,
                    @NonNull transformer: LifecycleTransformer<Any>){
        Observable.just(story)
                .compose(transformer)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .subscribe { mDatabaseHelper.updateStory(story) }

    }
}