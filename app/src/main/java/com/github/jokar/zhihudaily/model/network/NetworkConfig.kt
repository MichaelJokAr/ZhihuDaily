package com.sunagy.mazcloud.model.network

import com.github.jokar.zhihudaily.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/6/11.
 */
class NetworkConfig() {
    private val DEFAULT_TIMEOUT = 15
    private val BASE_URL = ""

    lateinit var retrofit: Retrofit

    init {
        initRetrofit()
    }

    companion object {
        @Volatile
        var INSTANCE: NetworkConfig? = null

        fun getInstance(): NetworkConfig {
            if (INSTANCE == null) {
                synchronized(NetworkConfig::class) {
                    if (INSTANCE == null) {
                        INSTANCE = NetworkConfig()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    private fun initRetrofit() {
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

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


}