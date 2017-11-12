package com.github.jokar.zhihudaily.ui.adapter.main

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.support.percent.PercentFrameLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.utils.extension.load
import com.github.jokar.zhihudaily.model.entities.theme.ThemeEntity
import com.github.jokar.zhihudaily.ui.adapter.base.AdapterItemClickListener
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.layout.StoryAdapterItemView
import com.github.jokar.zhihudaily.ui.layout.ThemeAdapterItemView
import com.github.jokar.zhihudaily.utils.image.ImageLoader

/**
 * Created by JokAr on 2017/7/4.
 */
class ThemeAdapter(var fragment: Fragment,
                   event: Lifecycle.Event,
                   var data: ThemeEntity)
    : BaseRecyclerAdapter<BaseViewHolder>(fragment.context, fragment, event) {

    override fun getItemCount(): Int {
        return data.stories.size + 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        when (viewType) {
        //head
            0 ->
                return HeadHolder(ThemeAdapterItemView.createHeadItemView(fragment.context), fragment.context)
        //编辑
            1 ->
                return EditorHolder(ThemeAdapterItemView.createEditorItemView(fragment.context), fragment.context)
        //有图片
            2 ->
                return ViewHolderWithImage(StoryAdapterItemView.createStoryItemView(fragment.context), fragment.context)
        //无图片
            3 ->
                return ViewHolder(ThemeAdapterItemView.createStoryItemView(fragment.context), fragment.context)
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        } else if (position == 1) {
            return 1
        } else {
            val storyEntity = data.stories[position - 2]
            if (storyEntity.images != null && storyEntity.images!!.size > 0) {
                return 2
            } else {
                return 3
            }
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        if (position != 0 && position != 1) {
            super.onBindViewHolder(viewHolder, position - 2)
        }
        when (getItemViewType(position)) {
            0 -> {
                //head
                setHeadData(viewHolder as HeadHolder)
            }
            1 -> {
                //编辑
                setEditorData(viewHolder as EditorHolder)
            }
            2 -> {
                //有图片
                val storyEntity = data.stories[position - 2]
                var holder: ViewHolderWithImage = viewHolder as ViewHolderWithImage
                loadImage(holder.image, storyEntity.images!![0])
                setTitle(holder.tvTitle, storyEntity.title!!)
            }
            3 -> {
                //无图片
                val storyEntity = data.stories[position - 2]
                var holder: ViewHolder = viewHolder as ViewHolder
                setTitle(holder.tvTitle, storyEntity.title!!)
            }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder?) {
        super.onViewRecycled(holder)
        if (holder is ViewHolderWithImage) {
            ImageLoader.clear(fragment.context, holder.image)
        }
    }

    /**
     * 头部数据
     */
    private fun setHeadData(holder: HeadHolder) {
        loadImage(holder.image, data.image)

        setTitle(holder.tvTiTle, data.description)
    }

    private fun loadImage(imageView: ImageView, url: String) {
        imageView.load(url)
    }

    fun setTitle(tvTitle: TextView, title: String) {
        tvTitle.text = title
    }

    /**
     * 编辑
     */
    private fun setEditorData(holder: EditorHolder) {
        var adapter = EditorAdapter(fragment.context, lifecycle, event, data.editors)
        holder.recyclerView.adapter = adapter
        adapter.clickListener = object : AdapterItemClickListener {
            override fun itemClickListener(position: Int) {

            }
        }
    }


    class HeadHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var image: ImageView = find(R.id.image)
        var tvTiTle: TextView = find(R.id.tvTitle)
    }

    /**
     * 编辑
     */
    class EditorHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context) {
        var recyclerView: RecyclerView = find(R.id.recyclerView)
    }

    /**
     * 内容-有图片
     */
    class ViewHolderWithImage(itemView: View, context: Context)
        : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.text)
        var image: ImageView = find(R.id.image)
        var percentFrameLayout: PercentFrameLayout = find(R.id.percentFrameLayout)
    }

    /**
     * 内容-没有图片
     */
    class ViewHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.text)
    }
}