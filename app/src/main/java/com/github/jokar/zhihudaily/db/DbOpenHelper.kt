package com.github.jokar.zhihudaily.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by JokAr on 2017/6/19.
 */
class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, "daily.db", null, 2) {

    val DAILY_TABLE: String = "CREATE TABLE " +
            "story (" +
            "id INTEGER PRIMARY KEY, " +
            "images TEXT, " +
            "title TEXT, " +
            "read INTEGER DEFAULT 0, " +
            "like INTEGER DEFAULT 0, " +
            "collection INTEGER DEFAULT 0, " +
            "date INTEGER); "

    val TOP_STORY_TABLE: String
            = "CREATE TABLE " +
            "topStory (" +
            "id INTEGER PRIMARY KEY, " +
            "image TEXT, " +
            "title TEXT, " +
            "date INTEGER); "

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DAILY_TABLE)
        db?.execSQL(TOP_STORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion == 2)
            db?.execSQL(TOP_STORY_TABLE)
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

    fun closeDB() {

    }
}