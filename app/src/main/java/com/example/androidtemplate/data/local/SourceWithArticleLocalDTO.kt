package com.example.androidtemplate.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class SourceWithArticleLocalDTO (
    @Embedded val source: SourceLocalDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "sourceId"
    )
    val articles: List<ArticleLocalDTO>
)