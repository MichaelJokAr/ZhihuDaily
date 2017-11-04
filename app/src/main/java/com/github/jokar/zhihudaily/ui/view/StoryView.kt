package com.github.jokar.zhihudaily.ui.view.common

import com.github.jokar.zhihudaily.model.entities.story.LatestStory
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity

/**
 * Created by JokAr on 2017/6/21.
 */
interface StoryView : SingleDataView<LatestStory> {

    /**
     * 加载更多数据
     */
    fun loadMoreData(data: ArrayList<StoryEntity>)

    /**
     * 请求/加载更多失败
     */
    fun loadMoreFail(e: Throwable)


}