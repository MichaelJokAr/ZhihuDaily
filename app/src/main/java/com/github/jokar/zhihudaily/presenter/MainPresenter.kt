package com.github.jokar.zhihudaily.presenter

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.model.entities.ThemeEntities
import com.github.jokar.zhihudaily.model.event.MainModel
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.ui.activity.MainView
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/15.
 */
class MainPresenter @Inject constructor(var mainModel: MainModel?, var view: MainView?) : BasePresenter {

    fun getThemes(@NonNull transformer: LifecycleTransformer<ThemeEntities>) {

        mainModel?.getThemes(transformer,
                object : ListDataCallBack<MainMenu> {
                    override fun data(data: ArrayList<MainMenu>) {

                    }
                })
    }

    override fun destroy() {
        mainModel = null
        view = null
    }
}