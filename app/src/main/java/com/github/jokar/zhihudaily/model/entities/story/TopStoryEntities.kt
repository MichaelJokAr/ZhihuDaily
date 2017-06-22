package com.github.jokar.zhihudaily.model.entities.story

import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/20.
 */
data class TopStoryEntities(@SerializedName("image") var image: String,
                            @SerializedName("type") var type: Int?,
                            @SerializedName("id") var id: Int,
                            @SerializedName("ga_prefix") var ga_prefix: String?,
                            @SerializedName("title") var title: String?) {
    var date: Long = 0
    var read: Int = 0
    var like: Int = 0
    var collection: Int = 0

    override fun toString(): String {
        return "StoryEntities(images=$image, " +
                "type=$type," +
                "id=$id, " +
                "ga_prefix=$ga_prefix," +
                "title=$title, " +
                "date=$date," +
                "read=$read," +
                "like=$like," +
                "collection=$collection)"
    }


}