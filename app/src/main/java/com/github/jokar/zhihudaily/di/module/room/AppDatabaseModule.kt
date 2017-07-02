package com.github.jokar.zhihudaily.di.module.room

import android.content.Context
import com.github.jokar.zhihudaily.room.AppDatabaseHelper
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/30.
 */
@Module
class AppDatabaseModule(var context: Context) {

    @Provides
    fun dataBaseProvider(): AppDatabaseHelper {
        return AppDatabaseHelper.getInstance(context)
    }
}