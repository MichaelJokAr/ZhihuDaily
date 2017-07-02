package com.github.jokar.zhihudaily.model.network.services

import com.github.jokar.zhihudaily.model.entities.ThemeEntities
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by JokAr on 2017/6/15.
 */
interface ThemesServices {

    /**
     * 获取主题
     */
    @GET("themes")
    fun getTheme(): Observable<ThemeEntities>
}