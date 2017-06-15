package com.github.jokar.zhihudaily.di.module.view

import com.github.jokar.zhihudaily.ui.activity.MainView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/15.
 */
@Module
class MainViewModule(var view:MainView) {

    @Provides
    fun viewProvider():MainView{
        return view
    }
}