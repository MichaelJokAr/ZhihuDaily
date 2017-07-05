package com.github.jokar.zhihudaily.utils.system

import android.content.Context
import android.widget.Toast

/**
 * Created by JokAr on 2017/7/5.
 */
object JToast {

    fun toast(context: Context, message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}