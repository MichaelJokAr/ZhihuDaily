package com.github.jokar.zhihudaily.di.component

import com.github.jokar.zhihudaily.app.MyApplication



/**
 * Created by JokAr on 2017/6/15.
 */

interface AppComponent {
    fun inject(app: MyApplication)
}