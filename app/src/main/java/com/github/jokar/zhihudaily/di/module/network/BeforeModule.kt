package com.github.jokar.zhihudaily.di.module.network

import com.github.jokar.zhihudaily.model.network.services.BeforeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by JokAr on 2017/6/21.
 */
@Module
class BeforeModule {

    @Provides
    fun beforeProvider(retrofit: Retrofit):BeforeService{
        return retrofit.create(BeforeService::class.java)
    }
}