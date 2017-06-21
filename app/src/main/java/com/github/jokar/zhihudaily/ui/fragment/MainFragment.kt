package com.github.jokar.zhihudaily.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.ui.view.common.LoadMoreView
import com.github.jokar.zhihudaily.widget.LazyFragment

/**
 * 首页
 * Created by JokAr on 2017/6/19.
 */
class MainFragment :LazyFragment(),LoadMoreView<StoryEntities>{
    
    override fun initViews(view: View) {

    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragmnet_main,container, false)
    }
    /**
     * 请求数据开始
     */
    override fun getDataStart() {
        
    }

    /**
     * 加载数据
     */
    override fun loadData(data: ArrayList<StoryEntities>) {
    }

    /**
     * 加载完成
     */
    override fun loadComplete() {
       
    }

    /**
     * 请求/加载失败
     */
    override fun fail(e: Throwable) {
        
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData(data: ArrayList<StoryEntities>) {
        
    }

    /**
     * 请求/加载更多失败
     */
    override fun loadMoreFail(e: Throwable) {
        
    }


}