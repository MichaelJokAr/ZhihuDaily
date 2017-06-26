package com.github.jokar.zhihudaily.model.entities.story

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/25.
 */
data class StoryDetail(@SerializedName("body") var body: String,
                       @SerializedName("image_source") var image_source: String,
                       @SerializedName("title") var title: String,
                       @SerializedName("image") var image: String,
                       @SerializedName("share_url") var share_url: String,
                       @SerializedName("js") var js: Array<String>,
                       @SerializedName("images") var images: Array<String>,
                       @SerializedName("id") var id: Int,
                       @SerializedName("css") var css: Array<String>) : Parcelable {
    var like: Int = 0

    var collection: Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoryDetail> = object : Parcelable.Creator<StoryDetail> {
            override fun createFromParcel(source: Parcel): StoryDetail = StoryDetail(source)
            override fun newArray(size: Int): Array<StoryDetail?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArray(),
            source.createStringArray(),
            source.readInt(),
            source.createStringArray()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(body)
        dest.writeString(image_source)
        dest.writeString(title)
        dest.writeString(image)
        dest.writeString(share_url)
        dest.writeStringArray(js)
        dest.writeStringArray(images)
        dest.writeInt(id)
        dest.writeStringArray(css)
    }
}