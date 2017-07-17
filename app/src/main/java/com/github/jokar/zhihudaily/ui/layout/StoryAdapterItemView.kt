package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.percent.percentFrameLayout

/**
 * Created by JokAr on 2017/7/17.
 */
object StoryAdapterItemView {

    /**
     * 顶部view
     */
    fun createTopHeadStoryItemView(context: Context): View {
        return with(context) {
            linearLayout {
                orientation = android.widget.LinearLayout.VERTICAL
                imageView {
                    id = com.github.jokar.zhihudaily.R.id.image
                    imageResource = com.github.jokar.zhihudaily.R.mipmap.splash
                    scaleType = android.widget.ImageView.ScaleType.FIT_XY
                }.lparams(width = org.jetbrains.anko.matchParent, height = org.jetbrains.anko.matchParent)
            }
        }
    }

    fun createStoryItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                cardView {
                    percentFrameLayout {
                        id = R.id.percentFrameLayout
                        lparams(width = matchParent, height = dip(95))
                        backgroundResource = CommonView.selectableItemBackground(context)
                        setPadding(dip(10), dip(10), dip(10), dip(10))

                        textView {
                            id = R.id.text
                            gravity = Gravity.LEFT
                            setPadding(0, 0, dip(10), 0)
                            textColor = Color.BLACK
                            textSize = 17f
                        }.lparams(width = matchParent, height = dip(0)) {
                            percentLayoutInfo.heightPercent = 1f
                            percentLayoutInfo.widthPercent = 0.75f
                            gravity = Gravity.LEFT or Gravity.TOP
                        }

                        imageView {
                            id = R.id.image
                            scaleType = ImageView.ScaleType.FIT_XY
                        }.lparams(width = matchParent, height = dip(0)) {
                            percentLayoutInfo.heightPercent = 1f
                            percentLayoutInfo.widthPercent = 0.25f
                            gravity = Gravity.RIGHT or Gravity.TOP
                        }

                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                }
            }
        }
    }

    fun createStoryTimeItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.text
                    textColor = Color.parseColor("#6f6f6f")
                    textSize = 15f
                }.lparams(width = wrapContent, height = wrapContent) {
                    setPadding(dip(10), dip(10), dip(10), dip(10))
                }
            }
        }
    }
}