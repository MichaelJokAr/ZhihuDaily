package com.github.jokar.zhihudaily.di.module.network

import com.github.jokar.zhihudaily.model.network.services.NewsServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by JokAr on 2017/6/25.
 */
@Module
class NewsModule {

    @Provides
    fun provider(retrofit: Retrofit): NewsServices {
        return retrofit.create(NewsServices::class.java)
    }
}