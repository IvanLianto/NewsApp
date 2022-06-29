package com.example.newsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.dao.FavoriteArticleDao
import com.example.newsapp.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun favoriteArticleDao(): FavoriteArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            synchronized(this) {
                var mInstance = instance

                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java,
                        "db_article"
                    ).fallbackToDestructiveMigration().build()
                    instance = mInstance
                }
                return mInstance
            }
        }
    }

}