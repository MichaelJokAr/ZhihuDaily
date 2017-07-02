package com.github.jokar.zhihudaily.di.module.presenter

import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.model.event.ThemeModel
import com.github.jokar.zhihudaily.ui.fragment.ThemeFragment
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/7/2.
 */
@Module
class ThemePresenterModule {

    @Provides
    fun modelProvider():ThemeModel{
        return ThemeModel()
    }

    @Provides
    fun viewProvider(fragment:ThemeFragment):SingleDataView<ThemeEntity>{
        return fragment
    }
}