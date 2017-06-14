package com.github.jokar.zhihudaily.di.module

import com.github.jokar.zhihudaily.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by JokAr on 2017/6/14.
 */
@Module
class NetworkModule {
    private val DEFAULT_TIMEOUT = 15
    private val BASE_URL = "https://news-at.zhihu.com/api/4/"

    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
