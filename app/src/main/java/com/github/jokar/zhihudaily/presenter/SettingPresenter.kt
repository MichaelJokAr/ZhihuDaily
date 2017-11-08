package com.github.jokar.zhihudaily.presenter

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.event.SettingModel
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.ui.view.SettingView
import com.trello.rxlifecycle2.LifecycleTransformer
import java.io.File
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/5.
 */
class SettingPresenter @Inject constructor(var model: SettingModel?,
                                           var view: SettingView?) : BasePresenter {


    fun getImageCacheSize(@NonNull lifecycle: LifecycleOwner) {
        model?.getImageCacheSize(lifecycle, object : SingleDataCallBack<String> {
            override fun data(data: String) {
                view?.showImageCacheSize(data)
            }
        })
    }

    fun clearImageCache() {
        model?.clearImageCache(object : SingleDataCallBack<String> {
            override fun data(data: String) {
                view?.showClearSuccess()
                view?.showImageCacheSize(data)
            }
        })
    }

    override fun destroy() {
        model = null
        view = null
    }
}