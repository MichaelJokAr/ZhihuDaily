package com.github.jokar.zhihudaily.ui.adapter.main

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.theme.EditorEntity
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.layout.EditorAdapterItemView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.widget.CircleImageView
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by JokAr on 2017/7/4.
 */
class EditorAdapter(var context: Context,
                    transformer: LifecycleTransformer<Any>,
                    var editors: ArrayList<EditorEntity>?)
    : BaseRecyclerAdapter<EditorAdapter.ViewHolder>(context, transformer) {
    override fun getItemCount(): Int {
        return editors?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(EditorAdapterItemView.createItemView(context), context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val url = editors?.get(position)?.avatar
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.loadImage(context,
                    url!!,
                    R.mipmap.image_small_default,
                    viewHolder.image)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        ImageLoader.clear(context, holder.image)
    }

    inner class ViewHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context) {
        var image: CircleImageView = find(R.id.image)
    }
}