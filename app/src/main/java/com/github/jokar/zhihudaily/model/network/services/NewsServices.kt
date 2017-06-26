package com.github.jokar.zhihudaily.model.network.services

import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by JokAr on 2017/6/25.
 */
interface NewsServices {
    /**
     * 获取过往消息
     */
    @GET("news/{id}")
    fun getNews(@Path("id") id: Int): Observable<StoryDetail>
}