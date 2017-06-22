package com.github.jokar.zhihudaily.ui.adapter.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.percent.PercentFrameLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntities
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.adapter.base.LoadMoreAdapter
import com.github.jokar.zhihudaily.ui.adapter.viewpager.ViewPagerAdapter
import com.github.jokar.zhihudaily.ui.fragment.TopStoryFragment
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.utils.rxjava.ViewUtils
import com.rd.PageIndicatorView
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.functions.Consumer


/**
 * Created by JokAr on 2017/6/21.
 */
class StoryAdapter(context: Context,
                   data: ArrayList<StoryEntities>,
                   transformer: LifecycleTransformer<Any>,
                   var topStories: ArrayList<TopStoryEntities>,
                   var fm: FragmentManager)
    : LoadMoreAdapter<StoryEntities>(context, data, transformer) {

    override fun bindView(viewHolder: BaseViewHolder, position: Int) {
        val storyEntities = data[position]
        when (getViewType(position)) {
            0 -> {
                setHeadView(viewHolder)
            }
            1 -> {
                var holder: TimeHolder = viewHolder as TimeHolder
                holder.text.text = storyEntities.date.toString()
            }
            2, 3 -> {
                var holder: ViewHolder = viewHolder as ViewHolder
                holder.tvTitle.text = storyEntities.title
                ImageLoader.loadImage(context,
                        storyEntities.images!![0],
                        R.mipmap.image_small_default,
                        holder.image)

                ViewUtils.viewClick(holder.percentFrameLayout, transformer,
                        Consumer<Any> { itemClickListener?.itemClickListener(position) })
            }
        }
    }

    private fun setHeadView(viewHolder: BaseViewHolder) {
        //TODO rcyclerView滚动时顶部view会被回收，再滚动到顶部时会重新走加载流程，原用户选择的页面状态会丢失
        var holder: HeadHolder = viewHolder as HeadHolder
        var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(fm)
        topStories.forEach({
            var fragment: TopStoryFragment = TopStoryFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("imageUrl", it.image)
            fragment.arguments = bundle
            pagerAdapter.addFragment(fragment, "")
        })
        holder.viewPager.adapter = pagerAdapter
        holder.viewPager.offscreenPageLimit = topStories.size

        holder.pageIndicatorView.setViewPager(holder.viewPager)

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
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        when (viewType) {
            0 ->
                return HeadHolder(inflater?.inflate(R.layout.item_story_head, parent,
                        false)!!, context)
            1 ->
                return TimeHolder(inflater?.inflate(R.layout.item_story_time, parent,
                        false)!!, context)
            2 ->
                return ViewHolder(inflater?.inflate(R.layout.item_story, parent,
                        false)!!, context, true)
            3 ->
                return ViewHolder(inflater?.inflate(R.layout.item_story, parent,
                        false)!!, context, false)
        }
        return null
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

    class HeadHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context) {
        var viewPager: ViewPager = find(R.id.viewPager)
        var tvTitle: TextView = find(R.id.tvTitle)
        var pageIndicatorView: PageIndicatorView = find(R.id.pageIndicatorView)

    }

    class TimeHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var text: TextView = find(R.id.text)
    }

    class ViewHolder(itemView: View, context: Context, var read: Boolean)
        : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.tvTitle)
        var image: ImageView = find(R.id.imageView)
        var percentFrameLayout: PercentFrameLayout = find(R.id.percentFrameLayout)

        init {
            if (read) {
                tvTitle.setTextColor(Color.parseColor("#777777"))
            }
        }
    }
}