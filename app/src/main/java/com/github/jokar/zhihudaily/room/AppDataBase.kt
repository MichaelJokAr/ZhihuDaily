package com.github.jokar.zhihudaily.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity
import com.github.jokar.zhihudaily.room.dao.StoryDao
import com.github.jokar.zhihudaily.room.dao.TopStoryDao

/**
 * Created by JokAr on 2017/6/30.
 */
@Database(entities = arrayOf(StoryEntity::class,
        TopStoryEntity::class), version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun topStoryDao(): TopStoryDao
}