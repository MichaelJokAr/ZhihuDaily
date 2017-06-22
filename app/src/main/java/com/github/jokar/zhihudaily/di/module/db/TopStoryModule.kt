package com.github.jokar.zhihudaily.di.module.db

import android.content.Context
import com.github.jokar.zhihudaily.db.TopStoryDB
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/22.
 */
@Module
class TopStoryModule(var context: Context) {

    @Provides
    fun provider():TopStoryDB{
        return TopStoryDB(context)
    }
}