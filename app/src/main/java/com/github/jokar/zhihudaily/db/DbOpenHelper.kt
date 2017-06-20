package com.github.jokar.zhihudaily.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by JokAr on 2017/6/19.
 */
class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, "daily.db", null, 1) {

    val DAILY_TABLE: String = "CREATE TABLE " +
            "story (" +
            "id INTEGER PRIMARY KEY, " +
            "images TEXT, " +
            "title TEXT, " +
            "read INTEGER DEFAULT 0, " +
            "like INTEGER DEFAULT 0, " +
            "collection INTEGER DEFAULT 0, " +
            "date INTEGER); "

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DAILY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        @Volatile
        var instance: DbOpenHelper? = null

        fun getInstance(context: Context): DbOpenHelper {
            if (instance == null) {
                synchronized(DbOpenHelper::class) {
                    if (instance == null) {
                        instance = DbOpenHelper(context.applicationContext)
                    }
                }
            }
            return instance!!
        }
    }

    fun closeDB(){

    }
}