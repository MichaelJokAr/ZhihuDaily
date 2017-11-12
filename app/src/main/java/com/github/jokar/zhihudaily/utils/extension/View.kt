package com.github.jokar.zhihudaily.utils.extension

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/11/12.
 */
fun View.click(owner: LifecycleOwner, function: () -> kotlin.Unit) {
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .bindUntilEvent(owner, Lifecycle.Event.ON_DESTROY)
            .subscribe {
                function()
            }
}

fun View.click(owner: LifecycleOwner, event: Lifecycle.Event,
               function: () -> kotlin.Unit) {
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .bindUntilEvent(owner, event)
            .subscribe {
                function()
            }
}

fun View.longClick(owner: LifecycleOwner, event: Lifecycle.Event,
               function: () -> kotlin.Unit) {
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .bindUntilEvent(owner, event)
            .subscribe {
                function()
            }
}
