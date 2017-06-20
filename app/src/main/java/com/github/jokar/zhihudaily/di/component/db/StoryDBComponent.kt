package com.github.jokar.zhihudaily.di.component.db

import com.github.jokar.zhihudaily.db.StoryDB
import com.github.jokar.zhihudaily.di.module.db.StoryDBModule
import dagger.Component

/**
 * Created by JokAr on 2017/6/20.
 */
@Component(modules = arrayOf(StoryDBModule::class))
interface StoryDBComponent {
    fun storyDB(): StoryDB
}