package com.github.jokar.zhihudaily.di.module.network

import com.github.jokar.zhihudaily.di.scoped.UserScope
import com.github.jokar.zhihudaily.model.network.services.ThemeServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by JokAr on 2017/6/15.
 */
@Module
class ThemeModule {

    @UserScope
    @Provides
    fun themeProvider(retrofit: Retrofit): ThemeServices {
        return retrofit.create(ThemeServices::class.java)
    }
}