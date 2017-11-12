package com.github.jokar.zhihudaily.ui.adapter.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.github.jokar.zhihudaily.utils.extension.click
import com.github.jokar.zhihudaily.utils.extension.longClick

/**
 * Created by JokAr on 2017/6/19.
 */
abstract class BaseRecyclerAdapter<VH : BaseViewHolder>(context: Context,
                                                        var lifecycle: LifecycleOwner,
                                                        var event: Lifecycle.Event)
    : RecyclerView.Adapter<VH>() {

    var inflater: LayoutInflater? = null
    var clickListener: AdapterItemClickListener? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {

        viewHolder.itemView.click(lifecycle, event) {
            clickListener?.itemClickListener(position)
        }

        viewHolder.itemView.longClick(lifecycle, event) {
            clickListener?.itemLongClickListener(position)
        }

    }
}