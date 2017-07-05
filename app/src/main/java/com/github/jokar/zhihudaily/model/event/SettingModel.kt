package com.github.jokar.zhihudaily.model.event

import android.content.Context
import android.support.annotation.NonNull
import android.text.format.Formatter
import com.bumptech.glide.load.engine.cache.DiskCache
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import java.io.File


/**
 * Created by JokAr on 2017/7/4.
 */
class SettingModel(var context: Context) {

    /**
     * 清除图片缓存
     */
    fun clearImageCache(@NonNull transformer: LifecycleTransformer<Any>,
                        @NonNull callBack: SingleDataCallBack<String>) {
        Flowable.just("")
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .compose(transformer)
                .map {
                    //清除图片缓存
                    ImageLoader.clearDiskCache(context)
                    val file = File(context.cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR)
                    val size = calculateSize(file)
                    return@map Formatter.formatFileSize(context, size)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s -> callBack.data("$s") }
    }

    /**
     * 获取图片缓存
     */
    fun getImageCacheSize(@NonNull transformer: LifecycleTransformer<File>,
                          @NonNull callBack: SingleDataCallBack<String>) {
        val file = File(context.cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR)
        Flowable.just(file)
                .subscribeOn(Schedulers.computation())
                .compose(transformer)
                .unsubscribeOn(Schedulers.computation())
                .map { file1 -> calculateSize(file1) }
                .map<Any> { size -> Formatter.formatFileSize(context, size) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s -> callBack.data("$s") }

    }

    fun calculateSize(dir: File?): Long {
        if (dir == null) {
            return 0
        }
        if (!dir.isDirectory) return dir.length()
        var result: Long = 0
        val children = dir.listFiles()
        if (children != null)
            for (child in children)
                result += calculateSize(child)
        return result
    }
}