package com.github.jokar.zhihudaily.utils.rxjava

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/6/22.
 */
object ViewUtils {

    fun viewClick(view:View,transformer: LifecycleTransformer<Any>,
                  consumer: Consumer<Any>){
        RxView.clicks(view)
                .compose(transformer)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(consumer)
    }
}