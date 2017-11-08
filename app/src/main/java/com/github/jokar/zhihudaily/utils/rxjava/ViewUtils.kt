package com.github.jokar.zhihudaily.utils.rxjava

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/6/22.
 */
object ViewUtils {

    fun viewClick(view: View,
                  lifecycle: LifecycleOwner,
                  event: Lifecycle.Event,
                  consumer: Consumer<Any>) {
        RxView.clicks(view)
                .bindUntilEvent(lifecycle, event)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(consumer)
    }
}