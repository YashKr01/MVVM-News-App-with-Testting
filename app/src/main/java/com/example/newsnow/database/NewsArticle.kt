package com.example.newsnow.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NEWS_ARTICLES")
data class NewsArticle(
    val title: String?,
    @PrimaryKey val url: String,
    val thumbnailUrl: String?,
    val description: String,
    val publishedAt: String,
    val isBookmarked: Boolean
)