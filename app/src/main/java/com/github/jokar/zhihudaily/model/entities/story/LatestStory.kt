package com.github.jokar.zhihudaily.model.entities.story

import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/20.
 */
data class LatestStory(@SerializedName("date") var date: Long,
                       @SerializedName("stories") var stories: ArrayList<StoryEntities>?,
                       @SerializedName("top_stories") var top_stories: ArrayList<TopStoryEntities>?)