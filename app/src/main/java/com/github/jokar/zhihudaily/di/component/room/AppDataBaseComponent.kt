package com.github.jokar.zhihudaily.di.component.room

import com.github.jokar.zhihudaily.di.module.room.AppDataBaseModule
import com.github.jokar.zhihudaily.room.AppDataBaseHelper
import dagger.Component

/**
 * Created by JokAr on 2017/6/30.
 */
@Component(modules = arrayOf(AppDataBaseModule::class))
interface AppDataBaseComponent {
    fun appDataBase(): AppDataBaseHelper
}