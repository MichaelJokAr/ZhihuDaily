package com.github.jokar.zhihudaily.di.module.model

import dagger.Module
import dagger.Provides

/**
 * Created by JokAr on 2017/6/15.
 */
@Module
class MainModelModule {

    @Provides
    fun provider(): MainModelModule {
        return MainModelModule()
    }
}