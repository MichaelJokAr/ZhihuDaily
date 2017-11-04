package com.github.jokar.zhihudaily.presenter.base

import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView

/**
 * Created by JokAr on 2017/11/4.
 */
class SingleDataViewCallBack<T>(private var view: SingleDataView<T>?) : SingleDataCallBack<T> {
    override fun data(data: T) {
        view?.loadData(data)
    }

    override fun onStart() {
        super.onStart()
        view?.getDataStart()
    }

    override fun onComplete() {
        super.onComplete()
        view?.loadComplete()
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        view?.fail(e)
    }
}