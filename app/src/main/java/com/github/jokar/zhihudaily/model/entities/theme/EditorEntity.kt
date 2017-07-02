package com.github.jokar.zhihudaily.model.entities.theme

import com.google.gson.annotations.SerializedName

/**
 * 编辑
 * Created by JokAr on 2017/7/2.
 */
data class EditorEntity(@SerializedName("url") var url:String,
                        @SerializedName("bio") var bio:String,
                        @SerializedName("id") var id:String,
                        @SerializedName("avatar") var avatar:String,
                        @SerializedName("name") var name:String) {


    override fun toString(): String {
        return "EditorEntity(url='$url', bio='$bio', id='$id', avatar='$avatar', name='$name')"
    }
}