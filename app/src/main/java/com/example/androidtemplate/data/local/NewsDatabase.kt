package com.example.androidtemplate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidtemplate.MainApplication
import com.example.androidtemplate.data.local.NewsDatabase.Companion.NEWS_DATABASE_NAME
import com.example.androidtemplate.helper.SingletonHolder

@Database(
    entities = [ArticleLocalDTO::class, SourceLocalDTO::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun getArticleDAO(): ArticleLocalDAO
    abstract fun getSourceDAO(): SourceLocalDAO

    companion object : SingletonHolder<NewsDatabase,Any>({
        Room.databaseBuilder(
            MainApplication.appContext,
            NewsDatabase::class.java,
            NEWS_DATABASE_NAME
        ).build()
    }){
        const val NEWS_DATABASE_NAME = "news_db"
    }
}