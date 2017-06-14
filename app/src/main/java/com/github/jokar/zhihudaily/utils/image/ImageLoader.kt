package com.github.jokar.zhihudaily.utils.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions



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


}