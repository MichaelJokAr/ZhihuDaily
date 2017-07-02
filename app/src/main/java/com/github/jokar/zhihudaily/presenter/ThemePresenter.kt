package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.model.event.ThemeModel
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/2.
 */
class ThemePresenter @Inject constructor(var model: ThemeModel?,
                                         var view: SingleDataView<ThemeEntity>?)
    :BasePresenter{


    fun getTheme(id: Int,
                 @NonNull transformer: LifecycleTransformer<ThemeEntity>) {
        model?.getTheme(id,transformer,
                object :SingleDataCallBack<ThemeEntity>{
                    override fun onStart() {
                        super.onStart()
                        view?.getDataStart()
                    }
                    override fun data(data: ThemeEntity) {
                        view?.loadData(data)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        view?.loadComplete()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view?.fail(e)
                    }
                })
    }

    override fun destroy() {
        model = null
        view = null
    }

}