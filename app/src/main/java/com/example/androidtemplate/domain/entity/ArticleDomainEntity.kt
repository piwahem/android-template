package com.example.androidtemplate.domain.entity

data class ArticleDomainEntity(
    val title: String,
    val author: String,
    val publishedAt: String,
    val urlToImage: String,
    val url: String,
    val sourceId: String,
    val isBookmark: Boolean
)