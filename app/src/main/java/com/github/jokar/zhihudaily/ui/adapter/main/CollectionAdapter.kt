package com.github.jokar.zhihudaily.ui.adapter.main

import android.content.Context
import android.support.percent.PercentFrameLayout
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by JokAr on 2017/7/4.
 */
class CollectionAdapter(var context: Context,
                        transformer: LifecycleTransformer<Any>,
                        var arrayList: ArrayList<StoryEntity>?)
    : BaseRecyclerAdapter<BaseViewHolder>(context, transformer) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        when (viewType) {
            0 ->
                return ViewHolderWithImage(inflater?.inflate(R.layout.item_story, parent,
                        false)!!, context)
            1 ->
                ViewHolder(inflater?.inflate(R.layout.item_story2, parent,
                        false)!!, context)
        }
        return null
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val storyEntity = arrayList?.get(position)
        when(getItemViewType(position)){
            0 ->{
                //有图片
                var holder: ViewHolderWithImage = viewHolder as ViewHolderWithImage
                loadImage(holder.image, storyEntity?.images!![0])
                setTitle(holder.tvTitle, storyEntity?.title!!)
            }
            1 ->{
                //无图片
                var holder: ViewHolder = viewHolder as ViewHolder
                setTitle(holder.tvTitle, storyEntity?.title!!)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        val storyEntity = arrayList?.get(position)

        if (storyEntity?.images != null && storyEntity?.images!!.size > 0) {
            return 0
        }
        return 1
    }


    fun loadImage(imageView: ImageView, url: String) {
        ImageLoader.loadImage(context,
                url,
                R.mipmap.image_small_default,
                imageView)
    }

    fun setTitle(tvTitle: TextView, title: String) {
        tvTitle.text = title
    }


    /**
     * 内容-有图片
     */
    class ViewHolderWithImage(itemView: View, context: Context)
        : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.tvTitle)
        var image: ImageView = find(R.id.imageView)
        var percentFrameLayout: PercentFrameLayout = find(R.id.percentFrameLayout)
    }

    /**
     * 内容-没有图片
     */
    class ViewHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context, false) {
        var tvTitle: TextView = find(R.id.tvTitle)
    }
}