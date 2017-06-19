package com.github.jokar.zhihudaily.ui.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.LifecycleTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/6/19.
 */
abstract class BaseRecyclerAdapter<VH : BaseViewHolder>(context: Context,
                                                        var transformer: LifecycleTransformer<Any>) : RecyclerView.Adapter<VH>() {

    var inflater: LayoutInflater? = null
    var clickListener: AdapterItemClickListener? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {

        RxView.clicks(viewHolder.itemView)
                .compose(transformer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe({
                    clickListener?.itemClickListener(position)
                })

        RxView.longClicks(viewHolder.itemView)
                .compose(transformer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe({
                    clickListener?.itemLongClickListener(position)
                })
    }
}