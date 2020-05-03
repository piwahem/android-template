package com.example.androidtemplate.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "source"
//    foreignKeys = [ForeignKey(entity = ArticleLocalDTO::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("sourceId"),
//        onUpdate =  ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE)]
)
data class SourceLocalDTO(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "language") val language: String,
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String
)
