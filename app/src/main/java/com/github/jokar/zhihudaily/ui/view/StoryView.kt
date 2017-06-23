package com.github.jokar.zhihudaily.ui.view.common

import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities

/**
 * Created by JokAr on 2017/6/21.
 */
interface StoryView {
    /**
     * 请求数据开始
     */
    fun getDataStart()

    /**
     * 加载数据
     */
    fun loadData(data:LatestStory)

    /**
     * 加载完成
     */
    fun loadComplete()

    /**
     * 请求/加载失败
     */
    fun fail(e:Throwable)

    /**
     * 加载更多数据
     */
    fun loadMoreData(data: ArrayList<StoryEntities>)

    /**
     * 请求/加载更多失败
     */
    fun loadMoreFail(e:Throwable)


}