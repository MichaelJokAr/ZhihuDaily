package com.github.jokar.zhihudaily.model.entities.theme

import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/7/2.
 */
data class ThemeEntity(@SerializedName("stories") var stories: ArrayList<StoryEntity>,
                       @SerializedName("description") var description:String,
                       @SerializedName("background") var background:String,
                       @SerializedName("name") var name:String,
                       @SerializedName("image") var image:String,
                       @SerializedName("editors") var editors:ArrayList<EditorEntity>,
                       @SerializedName("image_source")var image_source:String?) {
}