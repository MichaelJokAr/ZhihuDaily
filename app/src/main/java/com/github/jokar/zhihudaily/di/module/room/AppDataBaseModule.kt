package com.github.jokar.zhihudaily.di.module.room

import android.content.Context
import com.github.jokar.zhihudaily.room.AppDataBaseHelper
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/30.
 */
@Module
class AppDataBaseModule(var context: Context) {

    @Provides
    fun dataBaseProvider(): AppDataBaseHelper {
        return AppDataBaseHelper.getInstance(context)
    }
}