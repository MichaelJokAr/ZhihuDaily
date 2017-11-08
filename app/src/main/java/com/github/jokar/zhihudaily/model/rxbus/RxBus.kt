package com.github.jokar.zhihudaily.model.rxbus

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * Created by JokAr on 2017/6/23.
 */
class RxBus {
    var _bus: FlowableProcessor<Any>? = null
    var lastTime: Long = 0

    init {
        _bus = PublishProcessor.create<Any>().toSerialized()
    }

    companion object {
        @Volatile
        var INSTANCE: RxBus? = null

        fun getInstance(): RxBus {
            if (INSTANCE == null) {
                synchronized(RxBus::class) {
                    if (INSTANCE == null) {
                        INSTANCE = RxBus()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun post(o: Any) {
        if (System.currentTimeMillis() - lastTime < 100) {
            return
        }
        lastTime = System.currentTimeMillis()

        _bus?.onNext(o)
    }

    /**
     * dosen't designation to use special thread
     * ,It's depending on what the 'post' method use

     * @param lifecycleTransformer rxLifecycle
     * *
     * @return
     */
    fun toObservable(lifecycleTransformer: LifecycleTransformer<Any>): Flowable<Any> {
        return _bus
                ?.compose(lifecycleTransformer)
                ?.onBackpressureBuffer()!!
    }

    /**
     * designation use the MainThread, whatever the 'post' method use

     * @param lifecycleTransformer rxLifecycle
     * *
     * @return
     */
    fun toMainThreadObservable(lifecycle: LifecycleOwner, event: Lifecycle.Event): Flowable<Any> {
        return _bus
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.bindUntilEvent(lifecycle, event)
                ?.onBackpressureBuffer()!!
    }

    fun toUiThreadObservableWithoutBackPressure(lifecycleTransformer: LifecycleTransformer<Any>): Flowable<Any> {
        return _bus
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.compose(lifecycleTransformer)!!
    }

    /**
     * designation use the MainThread, whatever the 'post' method use

     * @return
     */
    fun toMainThreadObservable(): Flowable<Any> {
        return _bus
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.onBackpressureBuffer()!!
    }
}