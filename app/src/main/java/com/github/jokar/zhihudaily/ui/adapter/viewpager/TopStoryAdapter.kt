package com.github.jokar.zhihudaily.ui.adapter.viewpager

import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntities
import com.github.jokar.zhihudaily.utils.image.ImageLoader


/**
 * Created by JokAr on 2017/6/23.
 */
class TopStoryAdapter(var context: Context,
                      var viewList: ArrayList<View>,
                      var topStories: ArrayList<TopStoryEntities>) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = viewList[position]
        var image:ImageView = view.findViewById(R.id.image) as ImageView

        ImageLoader.loadImage(context,
                topStories[position].image,
                R.mipmap.image_small_default,
                image!!)
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