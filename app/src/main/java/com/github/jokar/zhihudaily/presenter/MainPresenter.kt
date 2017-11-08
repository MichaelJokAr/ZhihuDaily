package com.github.jokar.zhihudaily.presenter

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.model.event.MainModel
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.ui.view.MainView
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/15.
 */
class MainPresenter @Inject constructor(var mainModel: MainModel?, var view: MainView?)
    : BasePresenter {

    fun getThemes(@NonNull lifecycle: LifecycleOwner) {

        mainModel?.getThemes(lifecycle,
                object : ListDataCallBack<MainMenu> {
                    override fun data(data: ArrayList<MainMenu>) {
                        view?.loadThemes(data)
                    }
                })
    }

    override fun destroy() {
        mainModel = null
        view = null
    }
}