package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.widget.autoScrollViewPager
import com.github.jokar.zhihudaily.widget.pageIndicatorView
import com.rd.animation.type.AnimationType
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


    fun createHeadItemView(context: Context): View {
        return with(context) {
            relativeLayout {
                lparams(width = matchParent, height = dip(230))
                backgroundColor = Color.WHITE
                gravity = Gravity.BOTTOM

                autoScrollViewPager {
                    id = R.id.viewPager
                    duration = 5000
                }.lparams(width = matchParent, height = matchParent)

                view {
                    backgroundColor = Color.BLACK
                    alpha = 0.15f
                }.lparams(width = matchParent, height = matchParent)

                linearLayout {
                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = R.id.tvTitle
                        textSize = 20f
                        textColor = Color.WHITE
                        maxLines = 2
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(width = matchParent, height = wrapContent) {
                        setPadding(dip(10), dip(10), dip(10), dip(10))
                    }

                    pageIndicatorView {
                        id = R.id.pageIndicatorView
                        setAnimationType(AnimationType.DROP)
                        padding = 5
                        radius = 4
                    }.lparams(width = wrapContent, height = wrapContent) {
                        bottomMargin = dip(10)
                    }

                }.lparams(width = matchParent, height = wrapContent) {
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                }
            }
        }
    }
}