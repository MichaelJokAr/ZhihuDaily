package com.github.jokar.zhihudaily.app

import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.github.jokar.zhihudaily.utils.system.JLog

/**
 * Created by JokAr on 2017/10/6.
 */
class GlideUncaughtThrowableStrategy :GlideExecutor.UncaughtThrowableStrategy {

    override fun handle(e: Throwable) {
        JLog.e(e)
    }
}