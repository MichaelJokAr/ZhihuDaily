package com.github.jokar.zhihudaily.di.component.model

import com.github.jokar.zhihudaily.di.module.model.MainModelModule
import com.github.jokar.zhihudaily.di.scoped.UserScope
import dagger.Component

/**
 * Created by JokAr on 2017/6/15.
 */
@UserScope
@Component(modules = arrayOf(MainModelModule::class))
interface MainModelComponent {

}