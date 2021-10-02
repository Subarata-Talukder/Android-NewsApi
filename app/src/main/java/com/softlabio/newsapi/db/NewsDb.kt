package com.softlabio.newsapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softlabio.newsapi.dao.ArticleDao
import com.softlabio.newsapi.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converter::class)
abstract class NewsDb : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: NewsDb? = null
        private val LOCK = Any()
        private fun createDb(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDb::class.java,
                "news_db.db"
            ).build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDb(context).also { instance = it }
        }
    }
}