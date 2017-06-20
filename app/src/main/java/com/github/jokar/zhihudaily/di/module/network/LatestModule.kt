package com.github.jokar.zhihudaily.di.module.network

import com.github.jokar.zhihudaily.model.network.services.LatestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by JokAr on 2017/6/20.
 */
@Module
class LatestModule {

    @Provides
    fun latestProvider(retrofit: Retrofit): LatestService {
        return retrofit.create(LatestService::class.java)
    }
}