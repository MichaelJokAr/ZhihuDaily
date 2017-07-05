package com.github.jokar.zhihudaily.ui.view

/**
 * Created by JokAr on 2017/7/5.
 */
interface SettingView {

    /**
     * 显示图片缓存
     */
    fun showImageCacheSize(size: String)

    /**
     * 显示清除缓存成功
     */
    fun showClearSuccess()
}