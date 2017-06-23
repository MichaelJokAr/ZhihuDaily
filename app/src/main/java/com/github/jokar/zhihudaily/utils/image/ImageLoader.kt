package com.github.jokar.zhihudaily.utils.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.jokar.zhihudaily.utils.system.JLog


/**
 * Created by JokAr on 2017/6/14.
 */
object ImageLoader {

    /**
     * 加载图片
     * @param context
     * @param url
     * @param defaultImage
     * @param imageView
     */
    fun loadImage(context: Context,
                  url: String,
                  defaultImage: Int,
                  imageView: ImageView) {
        val options = RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)

    }

    /**
     * 清除

     * @param imageView
     */
    fun clear(context: Context, imageView: ImageView) {
        // TODO: 2016/11/14 主动清除view,会导致频繁gc(暂未发现Bug)
        try {
            Glide.with(context).clear(imageView)
        } catch (e: Exception) {
            JLog.e(e)
        }

    }
}