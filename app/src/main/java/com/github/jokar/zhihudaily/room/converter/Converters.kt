package com.github.jokar.zhihudaily.room.converter

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils


/**
 * Created by JokAr on 2017/6/30.
 */
class Converters {
    @TypeConverter
    open fun arrayToString(array: Array<String>): String {
        if (array == null || array.size === 0) {
            return ""
        }

        val builder = StringBuilder(array[0])
        for (i in 1..array.size - 1) {
            builder.append(",").append(array[i])
        }
        return builder.toString()
    }

    @TypeConverter
    open fun StringToArray(value: String): Array<String>? {
        return if (TextUtils.isEmpty(value)) null else value.split(",").toTypedArray()

    }
}