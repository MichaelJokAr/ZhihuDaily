package com.github.jokar.zhihudaily.model.network.result

import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import io.reactivex.observers.ResourceObserver

/**
 * Created by JokAr on 2017/6/20.
 */
class SingleResourceObserver<T>(var callBack: SingleDataCallBack<T>) : ResourceObserver<T>() {


    override fun onStart() {
        super.onStart()
        callBack.onStart()
    }

    override fun onComplete() {
        callBack.onComplete()
    }

    override fun onNext(t: T) {
        callBack.data(t)
    }

    override fun onError(e: Throwable) {
        callBack.onError(e)
    }
}