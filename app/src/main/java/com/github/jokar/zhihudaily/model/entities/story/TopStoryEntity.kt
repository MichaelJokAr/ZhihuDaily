package com.github.jokar.zhihudaily.model.entities.story

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/20.
 */
@Entity(tableName = "topStory")
data class TopStoryEntity(
        @PrimaryKey
        @SerializedName("id") var id: Int) {

    constructor() : this(0)

    @ColumnInfo(name = "image")
    @SerializedName("image") var image: String = ""

    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String? = null

    @ColumnInfo(name = "date")
    var date: Long = 0

    override fun toString(): String {
        return "TopStoryEntity(id=$id," +
                " image='$image'," +
                " title=$title," +
                " date=$date)"
    }


}