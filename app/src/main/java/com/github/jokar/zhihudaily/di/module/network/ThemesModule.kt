package com.github.jokar.zhihudaily.di.module.network

import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.network.services.ThemesServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by JokAr on 2017/6/15.
 */
@Module
class ThemesModule {

    @UserScope
    @Provides
    fun themeProvider(retrofit: Retrofit): ThemesServices {
        return retrofit.create(ThemesServices::class.java)
    }
}