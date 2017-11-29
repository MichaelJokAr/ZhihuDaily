package com.github.jokar.zhihudaily.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import com.afollestad.aesthetic.Aesthetic
import com.afollestad.aesthetic.BottomNavBgMode
import com.afollestad.aesthetic.BottomNavIconTextMode
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.presenter.base.BasePresenter
import com.github.jokar.zhihudaily.ui.layout.CommonView
import com.github.jokar.zhihudaily.widget.ColorIndicatorView
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import kotlinx.android.synthetic.main.activity_chameleon.*
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * Created by JokAr on 2017/8/19.
 */
class ChameleonActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun enter(context: Context) {
            val intent = Intent(context, ChameleonActivity::class.java)
            context.startActivity(intent)
        }
    }

    var chooseViewRes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chameleon)
        initToolbar(toolbar, "变色龙")

        init()
    }


    private fun init() {
        colorView_blue.setOnClickListener(this)
        colorView_red.setOnClickListener(this)
        colorView_orange.setOnClickListener(this)
        colorView_orangeYellow.setOnClickListener(this)
        colorView_grassGreen.setOnClickListener(this)
        colorView_green.setOnClickListener(this)
        colorView_hatsune.setOnClickListener(this)
        colorView_lightBlue.setOnClickListener(this)
        colorView_skyBlue.setOnClickListener(this)
        colorView_blue.setOnClickListener(this)
        colorView_purple.setOnClickListener(this)
        colorView_purpleRed.setOnClickListener(this)
        colorView_defaultColor.setOnClickListener(this)

        //set choose color
        val themeColorPrimary = CommonView.getThemeColorPrimary()
        setShowColor(themeColorPrimary)

        when (themeColorPrimary) {
            colorView_blue.colorValue -> {
                setDefaultChooseTheme(colorView_blue)
            }
            colorView_red.colorValue -> {
                setDefaultChooseTheme(colorView_red)
            }
            colorView_orange.colorValue -> {
                setDefaultChooseTheme(colorView_orange)
            }
            colorView_orangeYellow.colorValue -> {
                setDefaultChooseTheme(colorView_orangeYellow)
            }
            colorView_grassGreen.colorValue -> {
                setDefaultChooseTheme(colorView_grassGreen)
            }
            colorView_hatsune.colorValue -> {
                setDefaultChooseTheme(colorView_hatsune)
            }
            colorView_lightBlue.colorValue -> {
                setDefaultChooseTheme(colorView_lightBlue)
            }
            colorView_skyBlue.colorValue -> {
                setDefaultChooseTheme(colorView_skyBlue)
            }
            colorView_blue.colorValue -> {
                setDefaultChooseTheme(colorView_blue)
            }
            colorView_purple.colorValue -> {
                setDefaultChooseTheme(colorView_purple)
            }
            colorView_purpleRed.colorValue -> {
                setDefaultChooseTheme(colorView_purpleRed)
            }
            colorView_defaultColor.colorValue -> {
                setDefaultChooseTheme(colorView_defaultColor)
            }
        }

        colorPicker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                super.onColorSelected(color)
                applyTheme(color)
                //
                if (chooseViewRes > 0) {
                    findViewById<ColorIndicatorView>(chooseViewRes).setShowCheck(false)
                    chooseViewRes = 0
                }
            }
        })
    }

    override fun getPresent(): BasePresenter? {
        return null
    }

    override fun onClick(p0: View?) {
        val view: ColorIndicatorView = p0 as ColorIndicatorView

        applyTheme(view.colorValue!!)
        //选中状态
        if (chooseViewRes > 0) {
            findViewById<ColorIndicatorView>(chooseViewRes).setShowCheck(false)
        }
        view.setShowCheck(true)
        chooseViewRes = view.id
    }

    private fun setDefaultChooseTheme(colorIndicatorView: ColorIndicatorView) {
        colorIndicatorView.setShowCheck(true)
        chooseViewRes = colorIndicatorView.id
    }


    private fun applyTheme(color: Int) {
        Aesthetic.get()
                .colorPrimary(color)
                .colorAccent(color)
                .textColorSecondaryInverse(Color.WHITE)
                .colorStatusBarAuto()
                .colorNavigationBarAuto()
                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()

        //大圆显示
        setShowColor(color)
    }

    private fun setShowColor(color: Int) {
        colorPicker.setColor(color)
        imageView.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        //Hex
        var hexString = java.lang.Integer.toHexString(color)
        hexString = hexString.substring(2)
        tv_Hexadecimal.text = hexString

        //RGB
        val red = color and 0xff0000 shr 16
        val green = color and 0x00ff00 shr 8
        val blue = color and 0x0000ff
        tv_RGB.text = "$red,$green,$blue"
    }
}