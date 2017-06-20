package com.github.jokar.zhihudaily.model.event

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.component.network.DaggerThemeComponent
import com.github.jokar.zhihudaily.di.module.network.ThemeModule
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.model.entities.ThemeEntities
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.model.network.services.ThemeServices
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
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
    lateinit var services: ThemeServices

    init {
        DaggerThemeComponent.builder()
                .networkComponent(MyApplication.getInstance().getNetComponent())
                .themeModule(ThemeModule())
                .build()
                .inject(this)
    }

    /**
     * 获取主题
     */
    fun getThemes(@NonNull transformer: LifecycleTransformer<ThemeEntities>,
                  @NonNull callBack: ListDataCallBack<MainMenu>) {

        checkNotNull(transformer)
        checkNotNull(callBack)

        services.getTheme()
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map { data -> data?.themes!! }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    var menu: MainMenu = MainMenu(null, "首页", null, null)
                    menu.isClick = true
                    result.add(0, menu)
                    callBack.data(result)
                }, {
                    _ ->
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