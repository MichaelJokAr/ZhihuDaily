package com.github.jokar.zhihudaily.app

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * Created by JokAr on 2017/8/25.
 */
@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setMemoryCache(LruResourceCache(50 * 1024 * 1024))

        //自定义UncaughtThrowableStrategy
        val glideUncaughtThrowableStrategy = GlideUncaughtThrowableStrategy()
        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(glideUncaughtThrowableStrategy))
        builder.setResizeExecutor(GlideExecutor.newSourceExecutor(glideUncaughtThrowableStrategy))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory())
    }
}