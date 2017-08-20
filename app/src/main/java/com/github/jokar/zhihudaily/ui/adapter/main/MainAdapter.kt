package com.github.jokar.zhihudaily.ui.adapter.main

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.ui.adapter.base.BaseRecyclerAdapter
import com.github.jokar.zhihudaily.ui.adapter.base.BaseViewHolder
import com.github.jokar.zhihudaily.ui.layout.MainAdapterItemView
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.LifecycleTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by JokAr on 2017/6/19.
 */
class MainAdapter(var context: Context, transformer: LifecycleTransformer<Any>,
                  var arrayList: ArrayList<MainMenu>)
    : BaseRecyclerAdapter<BaseViewHolder>(context, transformer) {

    var adapterClickListener: AdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        when (viewType) {
            0 -> return HomeHolder(MainAdapterItemView.createHomeItemView(context),
                    context, true)
            1 -> return ViewHolder(MainAdapterItemView.createMainItemView(context),
                    context, true)
            2 -> return HomeHolder(MainAdapterItemView.createHomeItemView(context),
                    context, false)
            3 -> return ViewHolder(MainAdapterItemView.createMainItemView(context),
                    context, false)
            4 -> return HeadHolder(inflater?.inflate(R.layout.item_main_head,parent,
                    false)!!, context)
        }
        return null
    }

    override fun getItemCount(): Int {
        return arrayList.size + 1
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {

        if (position != 0) {
            val menu = arrayList[position - 1]
            when (getItemViewType(position)) {
                0, 2 -> {
                    var holder: HomeHolder = viewHolder as HomeHolder
                    holder.text.text = menu.name
                }
                1, 3 -> {
                    var holder: ViewHolder = viewHolder as ViewHolder
                    holder.text.text = menu.name
                }
            }

            RxView.clicks(viewHolder.itemView)
                    .compose(transformer)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe({
                        adapterClickListener?.itemClickListener(position)
                    })
        } else {
            var holder: HeadHolder = viewHolder as HeadHolder
            RxView.clicks(holder.tvCollection)
                    .compose(transformer)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe({
                        adapterClickListener?.collectionClick()
                    })
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 4
        }
        val menu = arrayList[position - 1]
        if (menu.isClick) {
            if (position == 1) {
                return 0
            } else
                return 1
        } else {
            if (position == 1) {
                return 2
            } else
                return 3
        }

    }

    class ViewHolder(itemView: View, context: Context, var isClick: Boolean)
        : BaseViewHolder(itemView, context, false) {
        var text: TextView = find(R.id.text)

        init {
            if (isClick) {
                text.setBackgroundColor(Color.parseColor("#f0f0f0"))
            } else {
                text.setBackgroundColor(Color.WHITE)
            }
        }
    }

    class HomeHolder(itemView: View, context: Context, var isClick: Boolean)
        : BaseViewHolder(itemView, context, false) {
        var text: TextView = find(R.id.text)

        init {
            if (isClick) {
                text.setBackgroundColor(Color.parseColor("#f0f0f0"))
            } else {
                text.setBackgroundColor(Color.WHITE)
            }
        }
    }

    class HeadHolder(itemView: View, context: Context)
        : BaseViewHolder(itemView, context, false) {
        var tvCollection: TextView = find(R.id.tvCollection)
    }

    interface AdapterClickListener {
        fun collectionClick()

        fun itemClickListener(position: Int)

    }
}