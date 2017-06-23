package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.db.StoryDB
import com.github.jokar.zhihudaily.db.TopStoryDB
import com.github.jokar.zhihudaily.di.component.db.DaggerStoryDBComponent
import com.github.jokar.zhihudaily.di.component.db.DaggerTopStoryDBComponent
import com.github.jokar.zhihudaily.di.component.network.DaggerLatestAndBeforeComponent
import com.github.jokar.zhihudaily.di.module.db.StoryDBModule
import com.github.jokar.zhihudaily.di.module.db.TopStoryModule
import com.github.jokar.zhihudaily.di.module.network.BeforeModule
import com.github.jokar.zhihudaily.di.module.network.LatestModule
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntities
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.model.network.result.ListResourceObserver
import com.github.jokar.zhihudaily.model.network.result.SingleResourceObserver
import com.github.jokar.zhihudaily.model.network.services.BeforeService
import com.github.jokar.zhihudaily.model.network.services.LatestService
import com.github.jokar.zhihudaily.utils.system.JLog
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by JokAr on 2017/6/20.
 */
class MainFragmentModel(var context: Context) {

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var latestService: LatestService

    @Inject
    lateinit var storyDB: StoryDB
    @Inject
    lateinit var topStoryDB: TopStoryDB

    @Inject
    lateinit var beforeService: BeforeService

    init {
        val storyDBComponent = DaggerStoryDBComponent.builder()
                .storyDBModule(StoryDBModule(context))
                .build()

        val topStoryDBComponent = DaggerTopStoryDBComponent.builder()
                .topStoryModule(TopStoryModule(context))
                .build()

        DaggerLatestAndBeforeComponent.builder()
                .storyDBComponent(storyDBComponent)
                .topStoryDBComponent(topStoryDBComponent)
                .networkComponent(MyApplication.getNetComponent())
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

        Observable.create(ObservableOnSubscribe<LatestStory> { e ->
            val calendar = Calendar.getInstance()
            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH) + 1
            var day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            var date: Long = 0
            date = year * 10000L
            month *= 100
            date += month
            date += day
            //先检测本地是否有
            JLog.w(date)
            var latestStory: LatestStory = LatestStory(date, null, null)
            var stories: ArrayList<StoryEntities>? = storyDB.getStoryByDate(date)
            var topStories: ArrayList<TopStoryEntities>? = topStoryDB.getTopStoriesByDate(date)

            //本地有就直接返回本地数据
            if (stories != null && stories?.size > 0) {
                //添加时间标题
                var timeTitle: StoryEntities = StoryEntities(null, null, 0, null, null)
                timeTitle.date = date
                stories.add(0, timeTitle)
                //添加head
                var head: StoryEntities = StoryEntities(null, null, -1, null, null)
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
                        callBack.data(t)
                        callBack.onComplete()
                        return@filter false
                    }
                    return@filter true
                }
                .flatMap {
                    latestService.getTheme()
                }
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { latestStory ->
                    //遍历，赋值时间
                    latestStory.stories?.forEach({
                        it.date = latestStory.date
                    })
                    //保存到数据表
                    storyDB.insert(latestStory.stories)
                    topStoryDB.insert(latestStory.top_stories!!, latestStory.date)

                    //添加时间标题
                    var timeTitle: StoryEntities = StoryEntities(null, null, 0, null, null)
                    timeTitle.date = latestStory.date
                    latestStory.stories?.add(0, timeTitle)
                    //添加head
                    var head: StoryEntities = StoryEntities(null, null, -1, null, null)
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
                       @NonNull callBack: ListDataCallBack<StoryEntities>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        Observable.create(ObservableOnSubscribe<ArrayList<StoryEntities>> { e ->
            //先检测本地是否有
            JLog.w(date)
            val stories: ArrayList<StoryEntities>? = storyDB.getStoryByDate(date)
            //本地有就直接返回本地数据
            if (stories != null && stories?.size > 0) {
                //添加时间标题
                var timeTitle: StoryEntities = StoryEntities(null, null, 0, null, null)
                timeTitle.date = date
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
                    beforeService.getTheme(date)
                }
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { (date, stories) ->
                    //遍历，赋值时间
                    stories?.forEach({
                        it.date = date
                    })
                    //保存到数据表
                    storyDB.insert(stories)
                    //添加时间标题
                    var timeTitle: StoryEntities = StoryEntities(null, null, 0, null, null)
                    timeTitle.date = date
                    stories?.add(0, timeTitle)
                    stories!!
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ListResourceObserver(callBack))

    }
}