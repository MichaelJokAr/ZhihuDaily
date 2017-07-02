package com.github.jokar.zhihudaily.model.event

import android.support.annotation.NonNull
import com.github.jokar.zhihudaily.app.MyApplication
import com.github.jokar.zhihudaily.di.component.network.DaggerThemeComponent
import com.github.jokar.zhihudaily.di.module.network.ThemeModule
import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.model.event.callback.SingleDataCallBack
import com.github.jokar.zhihudaily.model.network.services.ThemeServices
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by JokAr on 2017/7/2.
 */
class ThemeModel {

    @Inject
    lateinit var retrofit:Retrofit
    @Inject
    lateinit var service:ThemeServices

    init {
        DaggerThemeComponent.builder()
                .networkComponent(MyApplication.getNetComponent())
                .themeModule(ThemeModule())
                .build()
                .inject(this)
    }
    fun getTheme(id:Int,
                 @NonNull transformer: LifecycleTransformer<ThemeEntity>,
                 @NonNull callBack:SingleDataCallBack<ThemeEntity>){

        service.getTheme(id)
                .compose(transformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    callBack.data(result)
                }, {
                    error ->
                    callBack.onError(error)
                }, {
                    callBack.onComplete()
                }, {
                    callBack.onStart()
                })
    }
}