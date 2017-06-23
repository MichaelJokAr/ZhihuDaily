package com.github.jokar.zhihudaily.widget.recyclerView

import android.support.v7.widget.RecyclerView

/**
 * Created by JokAr on 2017/6/21.
 */
abstract class RecyclerOnScrollListener(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {
    var previousTotal: Int = 0
    var loading: Boolean = true

    var lastCompletelyVisiableItemPosition: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0


    var shouldLoading: Boolean = true

    var mHelper: RecyclerViewPositionHelper = RecyclerViewPositionHelper(recyclerView)

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)
        firstCompletelyVisibleItem(mHelper.findFirstCompletelyVisibleItemPosition())
        lastCompletelyVisibleItem(mHelper.findLastCompletelyVisibleItemPosition())

        if (shouldLoading) {
            visibleItemCount = recyclerView!!.childCount
            totalItemCount = mHelper.getItemCount()
            lastCompletelyVisiableItemPosition = mHelper.findLastCompletelyVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading
                    && visibleItemCount > 0
                    && lastCompletelyVisiableItemPosition >= totalItemCount - 3) {

                onLoadMore()
                loading = true
            }
        }
    }

    fun setCanLoading(loading: Boolean) {
        shouldLoading = loading
        if (shouldLoading)
            previousTotal = mHelper.getItemCount()
    }

    fun setCanLoad(loading: Boolean) {
        this.loading = loading
        if (loading)
            previousTotal = mHelper.getItemCount()
    }

    abstract fun onLoadMore()
    abstract fun lastCompletelyVisibleItem(position: Int)
    abstract fun firstCompletelyVisibleItem(position: Int)
}