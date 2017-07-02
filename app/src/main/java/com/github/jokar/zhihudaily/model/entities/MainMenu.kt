package com.github.jokar.zhihudaily.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/15.
 */
data class MainMenu(@SerializedName("description") var description: String?,
                    @SerializedName("name") var name: String,
                    @SerializedName("id") var id: Int?,
                    @SerializedName("thumbnail") var thumbnail: String?) {

    var isClick: Boolean = false
}