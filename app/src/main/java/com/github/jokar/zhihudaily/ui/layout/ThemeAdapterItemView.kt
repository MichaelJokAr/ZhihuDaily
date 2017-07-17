package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

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
}