package com.github.jokar.zhihudaily.model.network.services

import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by JokAr on 2017/6/20.
 */
interface LatestService {

    /**
     * 获取最新消息
     */
    @GET("news/latest")
    fun getTheme(): Observable<LatestStory>
}