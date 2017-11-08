package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.event.MainFragmentModel
import com.github.jokar.zhihudaily.ui.fragment.MainFragment
import com.github.jokar.zhihudaily.ui.view.common.StoryView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/21.
 */
@Module
class MainFragmentPresenterModule {

    @Provides
    fun modelProvider(fragment: MainFragment): MainFragmentModel {
        return MainFragmentModel(fragment)
    }

    @Provides
    fun viewProvider(fragment: MainFragment): StoryView {
        return fragment
    }
}