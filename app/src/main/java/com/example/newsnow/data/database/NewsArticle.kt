package com.example.newsnow.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "NEWS_ARTICLES")
data class NewsArticle(
    val title: String?,
    @PrimaryKey val url: String,
    val urlToImage: String?,
    val description: String?,
    val publishedAt: String,
    var isBookmarked: Boolean = false
) : Parcelable