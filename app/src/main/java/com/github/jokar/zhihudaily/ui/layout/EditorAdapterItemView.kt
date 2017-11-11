package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

/**
 * Created by JokAr on 2017/7/17.
 */
object EditorAdapterItemView {
    fun createItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = dip(55))
                orientation = LinearLayout.VERTICAL
                setPadding(dip(5), dip(5), dip(5), dip(5))
                imageView {
                    id = R.id.image
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }
}