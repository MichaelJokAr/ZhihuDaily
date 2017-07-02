package com.github.jokar.zhihudaily.room

import android.arch.persistence.room.Room
import android.content.Context
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity
import com.github.jokar.zhihudaily.utils.system.JLog

/**
 * Created by JokAr on 2017/6/30.
 */
class AppDatabaseHelper constructor(context: Context) {

    val appDataBase = Room.databaseBuilder(context, AppDatabase::class.java,
            "daily").build()!!

    companion object {
        @Volatile
        var INSTANCE: AppDatabaseHelper? = null

        fun getInstance(context: Context): AppDatabaseHelper {
            if (INSTANCE == null) {
                synchronized(AppDatabaseHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = AppDatabaseHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    /**
     * 插入story
     */
    fun insertStory(stories: ArrayList<StoryEntity>) {
        try {
            appDataBase.storyDao().insert(stories)
        } catch(e: Exception) {
            JLog.e(e)
        }
    }

    /**
     * 插入topStory
     */
    fun insertTopStory(topStories: ArrayList<TopStoryEntity>) {
        try {
            appDataBase.topStoryDao().insert(topStories)
        } catch(e: Exception) {
            JLog.e(e)
        }
    }

    /**
     * 根据时间获取story
     */
    fun getStoryByDate(date: Long): ArrayList<StoryEntity> {
        return appDataBase.storyDao().getStoryByDate(date) as ArrayList<StoryEntity>
    }

    /**
     *
     */
    fun getTopStoryByDate(date: Long):ArrayList<TopStoryEntity>{
        return appDataBase.topStoryDao().getTopStoryByDate(date) as ArrayList<TopStoryEntity>
    }
    /**
     * 根据id获取story
     */
    fun getStory(id: Int): StoryEntity{
        return appDataBase.storyDao().selectStory(id)
    }

    /**
     * 更新story;必须在非主线程中进行
     */
    fun updateStory(story: StoryEntity) {
        try {
            appDataBase.storyDao().updateStory(story)
        } catch(e: Exception) {
            JLog.e(e)
        }
    }
}