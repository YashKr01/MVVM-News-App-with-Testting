package com.example.newsnow.network.model

data class NewsArticle(
    val title: String?,
    val url: String,
    val description: String,
    val publishedAt: String,
    val urlToImage: String?
)