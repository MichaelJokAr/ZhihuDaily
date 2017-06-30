package com.github.jokar.zhihudaily.room

import android.arch.persistence.room.Room
import android.content.Context
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity

/**
 * Created by JokAr on 2017/6/30.
 */
class AppDataBaseHelper constructor(context: Context) {

    val appDataBase = Room.databaseBuilder(context, AppDataBase::class.java,
            "daily").build()!!

    companion object {
        @Volatile
        var INSTANCE: AppDataBaseHelper? = null

        fun getInstance(context: Context): AppDataBaseHelper {
            if (INSTANCE == null) {
                synchronized(AppDataBaseHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = AppDataBaseHelper(context.applicationContext)
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
        appDataBase.storyDao().insert(stories)
    }

    /**
     * 插入topStory
     */
    fun insertTopStory(topStories: ArrayList<TopStoryEntity>) {
        appDataBase.topStoryDao().insert(topStories)
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
    fun selectStory(id: Int): StoryEntity {
        return appDataBase.storyDao().selectStory(id)
    }

    /**
     * 更新story
     */
    fun updateStory(story: StoryEntity) {
        appDataBase.storyDao().updateStory(story)
    }
}