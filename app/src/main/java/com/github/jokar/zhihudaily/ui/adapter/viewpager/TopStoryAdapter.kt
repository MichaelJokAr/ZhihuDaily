package com.github.jokar.zhihudaily.ui.adapter.viewpager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntity
import com.github.jokar.zhihudaily.ui.adapter.base.AdapterItemClickListener
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.utils.rxjava.ViewUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.functions.Consumer


/**
 * Created by JokAr on 2017/6/23.
 */
class TopStoryAdapter(var context: Context,
                      var viewList: ArrayList<View>,
                      var transformer: LifecycleTransformer<Any>,
                      var topStories: ArrayList<TopStoryEntity>) : PagerAdapter() {
    var itemClickListener: AdapterItemClickListener? = null

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = viewList[position]
        var image: ImageView = view.findViewById(R.id.image) as ImageView

        ImageLoader.loadImage(context,
                topStories[position].image,
                R.mipmap.image_small_default,
                image!!)
        ViewUtils.viewClick(image, transformer,
                Consumer<Any> {
                    itemClickListener?.itemClickListener(position)
                })
        collection.addView(view)
        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

}