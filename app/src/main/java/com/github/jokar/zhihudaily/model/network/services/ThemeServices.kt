package com.github.jokar.zhihudaily.model.network.services

import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by JokAr on 2017/7/2.
 */
interface ThemeServices {
    /**
     * 获取过往消息
     */
    @GET("theme/{id}")
    fun getTheme(@Path("id") id: Int): Observable<ThemeEntity>
}