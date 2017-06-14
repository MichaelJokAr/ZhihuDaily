package com.github.jokar.zhihudaily.di.component.network

import com.github.jokar.zhihudaily.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by JokAr on 2017/6/14.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface NetworkComponent {
    fun retrofit():Retrofit
}