package com.example.newsnow.network.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(@SerializedName("articles") val articleDto: List<NewsArticleDto>)
