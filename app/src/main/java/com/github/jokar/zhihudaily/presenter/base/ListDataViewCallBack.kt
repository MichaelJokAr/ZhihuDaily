package com.github.jokar.zhihudaily.presenter.base

import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.ListDataView

/**
 * Created by JokAr on 2017/11/4.
 */
class ListDataViewCallBack<T>(private var view: ListDataView<T>?) : ListDataCallBack<T> {
    override fun data(data: ArrayList<T>) {
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