package com.github.jokar.zhihudaily.di.module.db

import android.content.Context
import com.github.jokar.zhihudaily.db.StoryDB
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/20.
 */
@Module
class StoryDBModule(var context: Context) {

    @Provides
    fun storyDBProvider(): StoryDB {
        return StoryDB(context)
    }
}