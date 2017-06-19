package com.github.jokar.zhihudaily.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by JokAr on 2017/6/19.
 */
class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, "daily.db", null, 1) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

}