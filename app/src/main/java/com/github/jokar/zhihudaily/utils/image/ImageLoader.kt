package com.github.jokar.zhihudaily.utils.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.jokar.zhihudaily.app.GlideApp
import com.github.jokar.zhihudaily.utils.system.JLog
import com.github.jokar.zhihudaily.widget.CircleImageView


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
                  imageView: ImageView?) {
        if (imageView != null) {

            GlideApp.with(context)
                    .load(url)
                    .placeholder(defaultImage)
                    .error(defaultImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
        }
    }

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
                  imageView: CircleImageView) {

        GlideApp.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?,
                                                 target: Target<Drawable>?, dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        imageView.setImageDrawable(resource)
                        return true
                    }

                })
                .into(imageView)

    }

    /**
     * 清除

     * @param imageView
     */
    fun clear(context: Context, imageView: ImageView?) {
        // TODO: 2016/11/14 主动清除view,会导致频繁gc(暂未发现Bug)
        try {
            GlideApp.with(context).clear(imageView)
        } catch (e: Exception) {
            JLog.e(e)
        }

    }

    fun clearDiskCache(context: Context) {
        //清除硬盘缓存
        GlideApp.get(context).clearDiskCache();
    }

    fun clearCache(context: Context) {
        //清除硬盘缓存
        Thread { GlideApp.get(context).clearDiskCache() }
        //清除缓存
        GlideApp.get(context).clearMemory()
    }
}