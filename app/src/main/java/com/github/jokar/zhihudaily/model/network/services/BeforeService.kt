package com.github.jokar.zhihudaily.model.network.services

import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by JokAr on 2017/6/20.
 */
interface BeforeService {

    /**
     * 获取过往消息
     */
    @GET("news/before/{date}")
    fun getTheme(@Path("date") date: Long): Observable<LatestStory>
}