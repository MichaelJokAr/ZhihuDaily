package com.github.jokar.zhihudaily.ui.adapter.base

/**
 * Created by JokAr on 2017/6/21.
 */
interface LoadMoreAdapterItemClickListener {
    fun itemClickListener(position: Int)

    fun loadMore()

    fun footViewClick()
}