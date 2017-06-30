package com.github.jokar.zhihudaily.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.utils.system.DateUtils
import com.github.jokar.zhihudaily.utils.system.JLog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by JokAr on 2017/6/19.
 */
class StoryDB(var context: Context) {

    val tableName: String = "story"
    val id: String = "_id"
    val images: String = "images"
    val title: String = "title"
    val read: String = "read"
    val like: String = "like"
    val collection: String = "collection"
    val date: String = "date"

    var dbHelper: DbOpenHelper = DbOpenHelper.getInstance(context)

    fun insert(story: StoryEntity) {

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

    fun insert(stores: ArrayList<StoryEntity>?) {
        val db = dbHelper.writableDatabase
        if (db.isOpen) {
            var gson: Gson? = Gson()

            try {
                //开始事务
                db.beginTransaction()
                stores?.forEach({
                    var contentValues = ContentValues()
                    contentValues.put(id, it.id)
                    var json: String = gson?.toJson(it.images)!!
                    contentValues.put(images, json)
                    contentValues.put(title, it.title)
                    contentValues.put(this.date, it.date)
                    db.insert(tableName, null, contentValues)
                })
                //设置事务处理成功
                db.setTransactionSuccessful()
            } catch(e: Exception) {
                JLog.e(e)
            } finally {
                //结束事务
                db.endTransaction()
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

    fun getLikeAndCollectionState(id: Int): Pair<Int, Int> {
        var db = dbHelper.readableDatabase
        var like: Int = 0
        var collection: Int = 0
        if (db.isOpen) {

            val cursor = db.query(tableName, null, "${this.id} = ?", arrayOf("$id"), null, null, null)
            if (cursor.moveToFirst()) {
                like = cursor.getInt(cursor.getColumnIndex(this.like))
                collection = cursor.getInt(cursor.getColumnIndex(this.collection))
            }
        }
        return Pair(like, collection)
    }

    /**
     * 获取所有收藏的story
     */
    fun getAllCollectedStory(): ArrayList<StoryEntity>? {
        val db = dbHelper.readableDatabase
        if (db.isOpen) {
            var arrayList: ArrayList<StoryEntity> = ArrayList()
            val cursor = db.query(tableName, null, "$collection = ?", arrayOf("1"), null, null, "$date desc")
            var gson: Gson? = Gson()
            while (cursor.moveToNext()) {
                var story: StoryEntity = getStory(cursor, gson)
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
    fun getStoryByDate(dateTime: Long): ArrayList<StoryEntity>? {
        val db = dbHelper.readableDatabase
        if (db.isOpen) {
            var arrayList: ArrayList<StoryEntity> = ArrayList()
            val cursor = db.query(tableName, null, "$date = ?", arrayOf("$dateTime"), null, null, "$date desc")
            var gson: Gson? = Gson()
            while (cursor.moveToNext()) {
                var story: StoryEntity = getStory(cursor, gson)
                arrayList.add(story)
            }
            cursor.close()
            gson = null
            return arrayList
        }

        return null
    }

    private fun getStory(cursor: Cursor, gson: Gson?): StoryEntity {
        var id = cursor.getInt(cursor.getColumnIndex(id))
        var image = cursor.getString(cursor.getColumnIndex(images))
        val type = object : TypeToken<Array<String>>() {}.type
        var images: Array<String> = gson?.fromJson(image, type)!!
        var title = cursor.getString(cursor.getColumnIndex(title))
        var story: StoryEntity = StoryEntity(id)
        story.date = cursor.getLong(cursor.getColumnIndex(date))
        story.dateString = DateUtils.judgmentTime(story.date)
        story.read = cursor.getInt(cursor.getColumnIndex(read))
        story.like = cursor.getInt(cursor.getColumnIndex(like))
        story.collection = cursor.getInt(cursor.getColumnIndex(collection))
        return story
    }
}