package com.github.jokar.zhihudaily.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
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
class TopStoryFragment : Fragment() {
    var image: ImageView? = null
    var imageUrl: String = ""

    override fun setArguments(args: Bundle) {
        super.setArguments(args)
        imageUrl = args.getString("imageUrl")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.item_top_story, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        image = view?.findViewById(R.id.image) as ImageView?

        ImageLoader.loadImage(context,
                imageUrl,
                R.mipmap.image_small_default,
                image!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}