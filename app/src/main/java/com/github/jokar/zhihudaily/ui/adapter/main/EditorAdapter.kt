package com.github.jokar.zhihudaily.ui.adapter.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.extension.loadCircle
import com.github.jokar.zhihudaily.model.entities.theme.EditorEntity
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.layout.EditorAdapterItemView
import com.github.jokar.zhihudaily.utils.image.ImageLoader

/**
 * Created by JokAr on 2017/7/4.
 */
class EditorAdapter(var context: Context,
                    lifecycle: LifecycleOwner,
                    event: Lifecycle.Event,
                    private var editors: ArrayList<EditorEntity>?)
    : BaseRecyclerAdapter<EditorAdapter.ViewHolder>(context, lifecycle, event) {
    override fun getItemCount(): Int {
        return editors?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(EditorAdapterItemView.createItemView(context), context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val url = editors?.get(position)?.avatar
        viewHolder.image.loadCircle(url)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        ImageLoader.clear(context, holder.image)
    }

    inner class ViewHolder(itemView: View, context: Context) : BaseViewHolder(itemView, context) {
        var image: ImageView = find(R.id.image)
    }
}