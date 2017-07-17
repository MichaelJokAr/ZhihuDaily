package com.github.jokar.zhihudaily.ui.layout

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import com.github.jokar.zhihudaily.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.toolbar

/**
 * Created by JokAr on 2017/7/16.
 */
object CommonView {

    fun selectableItemBackground(context: Context): Int {
        var outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return outValue.resourceId
    }


    fun getColorSchemeResources(): Int {
        return android.R.color.holo_blue_bright
    }

    fun toolbar(context: Context): View {
        return with(context) {
            themedAppBarLayout(R.style.Base_ThemeOverlay_AppCompat_Dark_ActionBar) {
                lparams(width = matchParent, height = dip(48))
                toolbar {
                    popupTheme = R.style.Base_Theme_AppCompat_Light_DarkActionBar
                    id = R.id.toolbar
                    backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }
}