package com.github.jokar.zhihudaily.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.github.jokar.zhihudaily.model.entities.story.TopStoryEntities
import com.github.jokar.zhihudaily.utils.system.JLog

/**
 * Created by JokAr on 2017/6/22.
 */
class TopStoryDB(var context: Context) {

    val tableName: String = "topStory"
    val id: String = "_id"
    val image: String = "image"
    val title: String = "title"
    val date: String = "date"

    var dbHelper: DbOpenHelper = DbOpenHelper.getInstance(context)

    fun insert(stories: ArrayList<TopStoryEntities>, date: Long) {
        val db = dbHelper.writableDatabase
        if (db.isOpen) {
            try {
                //开始事务
                db.beginTransaction()
                stories?.forEach({
                    var contentValues = ContentValues()
                    contentValues.put(id, it.id)
                    contentValues.put(image, it.image)
                    contentValues.put(title, it.title)
                    contentValues.put(this.date, date)
                    db.insert(tableName, null, contentValues)
                })
                //设置事务处理成功
                db.setTransactionSuccessful()
            } catch(e: Exception) {
                JLog.e(e)
            } finally {
                //结束事务
                db.endTransaction()
            }
        }
    }

    /**
     *
     */
    fun getTopStoriesByDate(dateTime: Long): ArrayList<TopStoryEntities>? {
        val db = dbHelper.readableDatabase
        if (db.isOpen) {
            var arrayList: ArrayList<TopStoryEntities> = ArrayList()
            val cursor = db.query(tableName, null, "$date = ?", arrayOf("$dateTime"), null, null, "$date desc")
            while (cursor.moveToNext()) {
                var story: TopStoryEntities = getStory(cursor)
                arrayList.add(story)
            }
            cursor.close()
            return arrayList
        }
        return null
    }

    private fun getStory(cursor: Cursor): TopStoryEntities {
        var id = cursor.getInt(cursor.getColumnIndex(id))
        var image = cursor.getString(cursor.getColumnIndex(image))
        var title = cursor.getString(cursor.getColumnIndex(title))
        var story: TopStoryEntities = TopStoryEntities(image, null, id, null, title)
        story.date = cursor.getLong(cursor.getColumnIndex(date))
        return story
    }
}