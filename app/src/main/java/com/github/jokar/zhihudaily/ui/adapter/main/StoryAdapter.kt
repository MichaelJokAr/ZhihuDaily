package com.github.jokar.zhihudaily.ui.adapter.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.graphics.Color
import android.support.percent.PercentFrameLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.extension.load
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity
import com.github.jokar.zhihudaily.ui.adapter.base.AdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.adapter.base.LoadMoreAdapter
import com.github.jokar.zhihudaily.ui.adapter.viewpager.TopStoryAdapter
import com.github.jokar.zhihudaily.ui.layout.StoryAdapterItemView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.utils.rxjava.ViewUtils
import com.github.jokar.zhihudaily.widget.viewpager.AutoScrollViewPager
import com.rd.PageIndicatorView
import io.reactivex.functions.Consumer


/**
 * Created by JokAr on 2017/6/21.
 */
class StoryAdapter(context: Context,
                   data: ArrayList<StoryEntity>,
                   lifecycle: LifecycleOwner,
                   event: Lifecycle.Event,
                   var topStories: ArrayList<TopStoryEntity>)
    : LoadMoreAdapter<StoryEntity>(context, data, lifecycle, event) {

    var headClickListener: HeadClickListener? = null


    override fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        when (viewType) {
            0 ->
                return HeadHolder(StoryAdapterItemView.createHeadItemView(context), context)
            1 ->
                return TimeHolder(StoryAdapterItemView.createStoryTimeItemView(context), context)
            2 ->
                return ViewHolder(StoryAdapterItemView.createStoryItemView(context), context, true)
            3 ->
                return ViewHolder(StoryAdapterItemView.createStoryItemView(context), context, false)
        }
        return null
    }

    override fun bindView(viewHolder: BaseViewHolder, position: Int) {
        val storyEntities = data[position]
        when (getViewType(position)) {
            0 -> {
                setHeadView(viewHolder)
            }
            1 -> {
                var holder: TimeHolder = viewHolder as TimeHolder
                holder.text.text = storyEntities.dateString
            }
            2, 3 -> {
                var holder: ViewHolder = viewHolder as ViewHolder
                holder.tvTitle.text = storyEntities.title
                holder.image.load(storyEntities.images?.get(0))

                ViewUtils.viewClick(holder.percentFrameLayout, lifecycle, event,
                        Consumer<Any> { itemClickListener?.itemClickListener(position) })
            }
        }
    }

    override fun getViewType(position: Int): Int {
        val entities = data[position]

        if (entities.id == -1) {
            //head
            return 0
        } else if (entities.id == 0) {
            //time
            return 1
        } else if (entities.read == 1) {
            return 2
        }
        return 3
    }

    override fun onViewRecycled(holder: BaseViewHolder?) {
        super.onViewRecycled(holder)
        if (holder is ViewHolder) {
            ImageLoader.clear(context, holder.image)
        }
    }

    private fun setHeadView(viewHolder: BaseViewHolder) {
        //TODO recyclerView滚动时顶部view会被回收，再滚动到顶部时会重新走加载流程，原用户选择的页面状态会丢失
        var holder: HeadHolder = viewHolder as HeadHolder
        var viewList: ArrayList<View> = arrayListOf()
        topStories.forEach({
            viewList.add(StoryAdapterItemView.createTopHeadStoryItemView(context))
        })
        //
        var pagerAdapter: TopStoryAdapter? = TopStoryAdapter(context, viewList, lifecycle, event,
                topStories)
        //点击事件
        pagerAdapter?.itemClickListener = object : AdapterItemClickListener {
            override fun itemClickListener(position: Int) {
                super.itemClickListener(position)
                headClickListener?.itemClick(position)
            }
        }
        holder.viewPager.adapter = pagerAdapter
        holder.viewPager.offscreenPageLimit = topStories.size

        //
        holder.tvTitle.text = topStories[0].title
        holder.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                holder.tvTitle.text = topStories[position].title
            }
        })

        holder.pageIndicatorView.setViewPager(holder.viewPager)
    }


    class HeadHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context) {
        var viewPager: AutoScrollViewPager = find(R.id.viewPager)
        var tvTitle: TextView = find(R.id.tvTitle)
        var pageIndicatorView: PageIndicatorView = find(R.id.pageIndicatorView)

    }

    class TimeHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var text: TextView = find(R.id.text)
    }

    class ViewHolder(itemView: View, context: Context, var read: Boolean)
        : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.text)
        var image: ImageView = find(R.id.image)
        var percentFrameLayout: PercentFrameLayout = find(R.id.percentFrameLayout)

        init {
            if (read) {
                tvTitle.setTextColor(Color.parseColor("#777777"))
            }
        }
    }

    /**
     * 头部viewPager点击事件
     */
    interface HeadClickListener {
        fun itemClick(position: Int)
    }
}