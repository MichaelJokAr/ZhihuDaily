package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by JokAr on 2017/7/17.
 */
object ThemeAdapterItemView {

    fun createStoryItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                cardView {
                    linearLayout {
                        id = R.id.percentFrameLayout
                        backgroundResource = CommonView.selectableItemBackground(context)
                        lparams(width = matchParent, height = dip(80))
                        orientation = LinearLayout.VERTICAL

                        textView {
                            id = R.id.text
                            gravity = Gravity.LEFT
                            textSize = 17f
                            textColor = Color.BLACK
                        }.lparams(width = matchParent, height = matchParent) {
                            setPadding(dip(10), dip(10), dip(10), dip(10))
                        }
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                }
            }
        }
    }


    fun createHeadItemView(context: Context): View {
        return with(context) {
            relativeLayout {
                lparams(width = matchParent, height = dip(230))
                gravity = Gravity.BOTTOM
                //image
                imageView {
                    id = R.id.image
                    imageResource = R.mipmap.splash
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams(width = matchParent, height = matchParent)

                view {
                    backgroundColor = Color.parseColor("#000000")
                    alpha = 0.15f
                }.lparams(width = matchParent, height = matchParent)

                textView {
                    id = R.id.tvTitle
                    gravity = Gravity.CENTER or Gravity.LEFT
                    textColor = Color.WHITE
                    textSize = 20f
                    setPadding(dip(10), dip(10), dip(10), dip(10))
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }


    fun createEditorItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = dip(60))
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                textView {
                    text = "主编"
                    textSize = 15f
                    textColor = Color.parseColor("#6f6f6f")
                    gravity = Gravity.CENTER
                    setPadding(dip(10), dip(10), dip(10), dip(10))
                }.lparams(width = wrapContent, height = matchParent)

                recyclerView {
                    id = R.id.recyclerView
                    layoutManager = GridLayoutManager(context, 7)
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }
}