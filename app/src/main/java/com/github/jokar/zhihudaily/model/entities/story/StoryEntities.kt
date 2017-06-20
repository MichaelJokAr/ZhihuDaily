package com.github.jokar.zhihudaily.model.entities.story

import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/20.
 */
data class StoryEntities(@SerializedName("images") var images: Array<String>?,
                         @SerializedName("type") var type: Int?,
                         @SerializedName("id") var id: Int,
                         @SerializedName("ga_prefix") var ga_prefix: String?,
                         @SerializedName("title") var title: String?) {
    var date: Long = 0
    var read: Int = 0
    var like: Int = 0
    var collection: Int = 0
}