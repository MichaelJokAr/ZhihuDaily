package com.github.jokar.zhihudaily.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.github.jokar.zhihudaily.model.entities.story.StoryEntities
import com.github.jokar.zhihudaily.utils.system.JLog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by JokAr on 2017/6/19.
 */
class StoryDB(var context: Context) {

    val tableName: String = "story"
    val id: String = "id"
    val images: String = "images"
    val title: String = "title"
    val read: String = "read"
    val like: String = "like"
    val collection: String = "collection"
    val date: String = "date"

    var dbHelper: DbOpenHelper = DbOpenHelper.getInstance(context)

    fun insert(story: StoryEntities) {

        val db = dbHelper.writableDatabase
        if (db.isOpen) {
            var contentValues = ContentValues()
            contentValues.put(id, story.id)
            var gson: Gson? = Gson()
            var json: String = gson?.toJson(images)!!
            gson = null
            contentValues.put(images, json)
            contentValues.put(title, story.title)
            contentValues.put(this.date, story.date)
            db.insert(tableName, null, contentValues)
        }
    }

    fun insert(stores: ArrayList<StoryEntities>?) {
        val db = dbHelper.writableDatabase
        if (db.isOpen) {
            var gson: Gson? = Gson()

            try {
                stores?.forEach({
                    var contentValues = ContentValues()
                    contentValues.put(id, it.id)
                    var json: String = gson?.toJson(it.images)!!
                    contentValues.put(images, json)
                    contentValues.put(title, it.title)
                    contentValues.put(this.date, it.date)
                    db.insert(tableName, null, contentValues)
                })
            } catch(e: Exception) {
                JLog.e(e)
            } finally {
                gson = null
            }
        }
    }

    /**
     *更新点赞
     */
    fun updateLike(id: Int, like: Boolean) {
        var contentValues = ContentValues()
        var value = 0
        if (like) {
            value = 1
        }
        contentValues.put(this.like, value)
        update(id, contentValues)
    }

    /**
     * 更新已读
     */
    fun updateRead(id: Int) {
        var contentValues = ContentValues()
        contentValues.put(read, 1)
        update(id, contentValues)
    }

    /**
     * 更新收藏
     */
    fun updateCollection(id: Int, collected: Boolean) {
        var contentValues = ContentValues()
        var value = 0
        if (collected) {
            value = 1
        }
        contentValues.put(collection, value)
        update(id, contentValues)
    }

    /**
     * 根据id更新表
     */
    fun update(idCode: Int, contentValues: ContentValues) {
        val db = dbHelper.writableDatabase
        if (db.isOpen) {
            db.update(tableName, contentValues, "$id = ?", arrayOf("$idCode"))
        }
    }

    /**
     * 获取所有收藏的story
     */
    fun getAllCollectedStory(): ArrayList<StoryEntities>? {
        val db = dbHelper.readableDatabase
        if (db.isOpen) {
            var arrayList: ArrayList<StoryEntities> = ArrayList()
            val cursor = db.query(tableName, null, "$collection = ?", arrayOf("1"), null, null, "$date desc")
            var gson: Gson? = Gson()
            while (cursor.moveToNext()) {
                var story: StoryEntities = getStory(cursor, gson)
                arrayList.add(story)
            }
            gson = null
            return arrayList
        }

        return null
    }

    /**
     * 根据时间获取story
     */
    fun getStoryByDate(dateTime: Long): ArrayList<StoryEntities>? {
        val db = dbHelper.readableDatabase
        if (db.isOpen) {
            var arrayList: ArrayList<StoryEntities> = ArrayList()
            val cursor = db.query(tableName, null, "$date = ?", arrayOf("$dateTime"), null, null, "$date desc")
            var gson: Gson? = Gson()
            while (cursor.moveToNext()) {
                var story: StoryEntities = getStory(cursor, gson)
                arrayList.add(story)
            }
            cursor.close()
            gson = null
            return arrayList
        }

        return null
    }

    private fun getStory(cursor: Cursor, gson: Gson?): StoryEntities {
        var id = cursor.getInt(cursor.getColumnIndex(id))
        var image = cursor.getString(cursor.getColumnIndex(images))
        val type = object : TypeToken<Array<String>>() {}.type
        var images: Array<String> = gson?.fromJson(image, type)!!
        var title = cursor.getString(cursor.getColumnIndex(title))
        var story: StoryEntities = StoryEntities(images, null, id, null, title)
        story.date = cursor.getLong(cursor.getColumnIndex(date))
        story.read = cursor.getInt(cursor.getColumnIndex(read))
        story.like = cursor.getInt(cursor.getColumnIndex(like))
        story.collection = cursor.getInt(cursor.getColumnIndex(collection))
        return story
    }
}