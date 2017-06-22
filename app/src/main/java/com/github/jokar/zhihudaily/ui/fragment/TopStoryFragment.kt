package com.github.jokar.zhihudaily.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.widget.LazyFragment

/**
 * Created by JokAr on 2017/6/22.
 */
class TopStoryFragment : LazyFragment() {
    var image: ImageView? = null
    var imageUrl: String = ""
    override fun setArguments(args: Bundle) {
        super.setArguments(args)
        imageUrl = args.getString("imageUrl")
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.item_top_story, container, false)
    }

    override fun initViews(view: View) {
        image = view.findViewById(R.id.image) as ImageView?
    }

    override fun loadData() {
        super.loadData()

        ImageLoader.loadImage(context,
                imageUrl,
                R.mipmap.image_small_default,
                image!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        image = null
    }
}