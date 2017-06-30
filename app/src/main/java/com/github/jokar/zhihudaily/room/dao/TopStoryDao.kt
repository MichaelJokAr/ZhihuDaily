package com.github.jokar.zhihudaily.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity

/**
 * Created by JokAr on 2017/6/30.
 */

@Dao
interface TopStoryDao {

    @Insert
    fun insert(topStories:ArrayList<TopStoryEntity>)

    /**
     * 根据时间获取
     */
    @Query("SELECT * FROM topStory where date = :arg0")
    fun getTopStoryByDate(date:Long):List<TopStoryEntity>
}