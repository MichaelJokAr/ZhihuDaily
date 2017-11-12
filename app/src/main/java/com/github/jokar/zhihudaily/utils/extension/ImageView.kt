package com.github.jokar.zhihudaily.utils.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.app.GlideApp

/**
 * Created by JokAr on 2017/11/11.
 */

/**
 * 加载图片
 */
fun ImageView.load(url: String?) {
    GlideApp.with(context)
            .load(url)
            .placeholder(R.mipmap.image_small_default)
            .error(R.mipmap.image_small_default)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}

/**
 * 加载成圆形图片
 */
fun ImageView.loadCircle(url: String?) {
    val drawable = transformDrawable(context, R.mipmap.image_small_default, CircleCrop())
    GlideApp.with(context)
            .load(url)
            .placeholder(drawable)
            .error(drawable)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}

fun transformDrawable(context: Context,
                      resourceId: Int,
                      transformation: Transformation<Bitmap>): BitmapDrawable {
    var drawable = ContextCompat.getDrawable(context, resourceId)
    var bitmap = Bitmap.createBitmap(drawable.minimumWidth, drawable.minimumHeight, Bitmap.Config.ARGB_8888)
    var canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    drawable.draw(canvas)

    //make
    var original = BitmapResource.obtain(bitmap, GlideApp.get(context).bitmapPool)
    var rounded = transformation.transform(context, original, bitmap.width, bitmap.height)

    if (original != null && !original.equals(rounded)) {
        original.recycle()
    }
    return BitmapDrawable(context.resources, rounded.get())
}