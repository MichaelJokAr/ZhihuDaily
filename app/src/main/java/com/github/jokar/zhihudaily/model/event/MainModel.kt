package com.github.jokar.zhihudaily.model.event

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.component.network.DaggerThemesComponent
import com.github.jokar.zhihudaily.di.module.network.ThemesModule
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.network.services.ThemesServices
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/15.
 */
class MainModel {
    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var mServices: ThemesServices

    init {

        DaggerThemesComponent.builder()
                .networkComponent(MyApplication.getNetComponent())
                .themesModule(ThemesModule())
                .build()
                .inject(this)
    }

    /**
     * 获取主题
     */
    fun getThemes(@NonNull lifecycle: LifecycleOwner,
                  @NonNull callBack: ListDataCallBack<MainMenu>) {


        checkNotNull(callBack)

        mServices.getTheme()
                .bindUntilEvent(lifecycle, Lifecycle.Event.ON_DESTROY)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { data -> data?.themes!! }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    var menu: MainMenu = MainMenu(null, "首页", null, null)
                    menu.isClick = true
                    result.add(0, menu)
                    callBack.data(result)
                }, { e ->
                    e.printStackTrace()
                    var array: ArrayList<MainMenu> = ArrayList()
                    var menu: MainMenu = MainMenu(null, "首页", null, null)
                    menu.isClick = true
                    array.add(menu)
                    callBack.data(array)
                }, {
                    callBack.onComplete()
                }, {
                    callBack.onStart()
                })
    }
}