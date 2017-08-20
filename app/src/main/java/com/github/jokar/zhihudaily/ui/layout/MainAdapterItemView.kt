package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.*


/**
 * Created by JokAr on 2017/7/17.
 */
object MainAdapterItemView {

    fun createHomeItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = dip(48))
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.text
                    text = "首页"
                    textColor = CommonView.getThemeColorPrimary()
                    textSize = 17f
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(16), 0, dip(20), 0)
                    setCompoundDrawables(getDrawable(context, R.mipmap.home), null, null, null)
                    compoundDrawablePadding = dip(15)
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }

    fun createMainItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = dip(48))
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.text
                    text = "日常心理学"
                    textColor = Color.BLACK
                    textSize = 17f
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setPadding(dip(16), 0, dip(20), 0)
                    setCompoundDrawables(null, null, getDrawable(context, R.mipmap.home_arrow), null)
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }


    fun createHeadItemView(context: Context): View {
        return with(context) {
            linearLayout {
                lparams(width = matchParent, height = dip(120))
                orientation = LinearLayout.VERTICAL
                backgroundColor = CommonView.getThemeColorPrimary()
                setPadding(dip(16), 0, dip(16), 0)

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        imageResource = R.mipmap.ic_logo
                    }.lparams(width = dip(40), height = dip(40)) {
                        topMargin = dip(10)
                    }

                    textView {
                        text = "知乎日报"
                        textColor = Color.WHITE
                        textSize = 17f
                        gravity = Gravity.CENTER or Gravity.LEFT
                        setPadding(dip(10), dip(10), dip(10), dip(10))
                    }.lparams(width = matchParent, height = wrapContent){
                        topMargin = dip(10)
                    }
                }.lparams(width = matchParent, height = wrapContent)

                textView {
                    id = R.id.tvCollection
                    text = "我的收藏"
                    textColor = Color.WHITE
                    textSize = 15f
                    gravity = Gravity.CENTER or Gravity.LEFT
                    setCompoundDrawables(getDrawable(context, R.mipmap.collect), null, null, null)
                    compoundDrawablePadding = dip(15)
                    backgroundResource = CommonView.selectableItemBackground(context)
                }.lparams(width = matchParent, height = dip(48)){
                    topMargin = dip(20)
                }
            }
        }
    }

    private fun getDrawable(context: Context, id: Int): Drawable {
        val drawable = context.resources.getDrawable(id)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        return drawable
    }
}