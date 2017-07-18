package com.github.jokar.zhihudaily.utils.system

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JokAr on 2017/6/23.
 */
object DateUtils {

    /**
     * 获取该时间的前一天
     */
    fun getBeforeDate(time: Long): Long {
        var format: SimpleDateFormat? = SimpleDateFormat("yyyy-MM-dd")
        //转换成Java时间
        var date: Date = changeToDate(time)

        //获取前一天时间
        var calendar: Calendar? = Calendar.getInstance()
        calendar?.time = date
        var day2 = calendar?.get(Calendar.DATE)!!
        calendar?.set(Calendar.DATE, day2 - 1)

        var beforeDate = format?.format(calendar?.time)!!
        format = null
        calendar = null

        //转换成知乎时间
        val list = beforeDate.split("-")
        var beforeTime: Long = 0
        beforeTime = list[0].toInt() * 10000L
        beforeTime += list[1].toInt() * 100
        beforeTime += list[2].toInt()

        JLog.d("beforeTime: $beforeTime")
        return beforeTime
    }

    /**
     * 判断时间
     */
    fun judgmentTime(time: Long): String {
        var date: Date = changeToDate(time)
        @SuppressLint("SimpleDateFormat")
        var format: SimpleDateFormat? = SimpleDateFormat("MM月dd日 EEEE")
        var string = format?.format(date)!!
        format = null
        return string
    }

    fun isThisTime(time: Long, pattern: String): Boolean {
        val date = Date(time)
        val sdf = SimpleDateFormat(pattern)
        val param = sdf.format(date)//参数时间
        val now = sdf.format(Date())//当前时间
        if (param == now) {
            return true
        }
        return false
    }

    fun changeToDate(time: Long): Date {
        var format: SimpleDateFormat? = SimpleDateFormat("yyyy-MM-dd")
        //转换成Java时间
        var year = time / 10000L
        var day = time % 10000L % 100
        var month = time % 10000L / 100

        var timeString = "$year-$month-$day"
        var date: Date = format?.parse(timeString)!!
        format = null

        return date
    }
}