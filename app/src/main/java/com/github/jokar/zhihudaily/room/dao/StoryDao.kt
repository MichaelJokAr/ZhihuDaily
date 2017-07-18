package com.github.jokar.zhihudaily.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity

/**
 * Created by JokAr on 2017/6/30.
 */
@Dao
interface StoryDao {

    /**
     * 插入story
     */
    @Insert
    fun insert(stories: ArrayList<StoryEntity>)

    /**
     * 插入
     */
    @Insert
    fun insert(story: StoryEntity)
    /**
     * 根据时间获取story
     */
    @Query("SELECT * FROM story WHERE date = :arg0 ")
    fun getStoryByDate(date: Long): List<StoryEntity>

    /**
     * 获取所有收藏的story
     */
    @Query("SELECT * FROM story WHERE collection = 1 order by date desc")
    fun getAllCollectedStory(): List<StoryEntity>

    /**
     * 更新story
     */
    @Update
    fun updateStory(story: StoryEntity)

    /**
     * 根据id获取story
     */
    @Query("SELECT * FROM story where id = :arg0 ")
    fun selectStory(id: Int): StoryEntity
}