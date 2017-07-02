package com.github.jokar.zhihudaily.di.component.room

import com.github.jokar.zhihudaily.di.module.room.AppDatabaseModule
import com.github.jokar.zhihudaily.room.AppDatabaseHelper
import dagger.Component

/**
 * Created by JokAr on 2017/6/30.
 */
@Component(modules = arrayOf(AppDatabaseModule::class))
interface AppDatabaseComponent {
    fun appDataBase(): AppDatabaseHelper
}