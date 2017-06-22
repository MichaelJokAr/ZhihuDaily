package com.github.jokar.zhihudaily.di.component.db

import com.github.jokar.zhihudaily.db.TopStoryDB
import com.github.jokar.zhihudaily.di.module.db.TopStoryModule
import dagger.Component

/**
 * Created by JokAr on 2017/6/22.
 */
@Component(modules = arrayOf(TopStoryModule::class))
interface TopStoryDBComponent {
    fun topStoryDB(): TopStoryDB
}