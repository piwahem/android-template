package com.example.androidtemplate.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "article"
//    foreignKeys = [ForeignKey(entity = SourceLocalDTO::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("sourceId"),
//        onUpdate =  ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE)]
)
data class ArticleLocalDTO(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String,
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "sourceId") val sourceId: String,
    @ColumnInfo(name = "isBookmark") val isBookmark: Boolean
)