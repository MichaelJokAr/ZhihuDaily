package com.github.jokar.zhihudaily.ui.adapter.main

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.utils.extension.load
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.layout.StoryAdapterItemView
import com.github.jokar.zhihudaily.ui.layout.ThemeAdapterItemView

/**
 * Created by JokAr on 2017/7/4.
 */
class CollectionAdapter(var activity: AppCompatActivity,
                        event: Lifecycle.Event,
                        private var arrayList: ArrayList<StoryEntity>?)
    : BaseRecyclerAdapter<BaseViewHolder>(activity, activity, event) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        when (viewType) {
            0 ->
                return ViewHolderWithImage(StoryAdapterItemView.createStoryItemView(activity), activity)
            1 ->
                ViewHolder(ThemeAdapterItemView.createStoryItemView(activity), activity)
        }
        return null
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val storyEntity = arrayList?.get(position)
        when (getItemViewType(position)) {
            0 -> {
                //有图片
                var holder: ViewHolderWithImage = viewHolder as ViewHolderWithImage
                loadImage(holder.image, storyEntity?.images!![0])
                setTitle(holder.tvTitle, storyEntity.title)
            }
            1 -> {
                //无图片
                var holder: ViewHolder = viewHolder as ViewHolder
                setTitle(holder.tvTitle, storyEntity?.title)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val storyEntity = arrayList?.get(position)

        if (storyEntity?.images != null && storyEntity.images!!.isNotEmpty()) {
            return 0
        }
        return 1
    }


    private fun loadImage(imageView: ImageView, url: String) {
        imageView.load(url)
    }

    private fun setTitle(tvTitle: TextView, title: String?) {
        tvTitle.text = title ?: ""
    }


    /**
     * 内容-有图片
     */
    class ViewHolderWithImage(itemView: View, context: Context)
        : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.text)
        var image: ImageView = find(R.id.image)
    }

    /**
     * 内容-没有图片
     */
    class ViewHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.text)
    }
}