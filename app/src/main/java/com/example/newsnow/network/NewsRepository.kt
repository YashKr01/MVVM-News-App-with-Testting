package com.example.newsnow.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsnow.database.NewsArticleDao
import com.example.newsnow.paging.NewsPagingSource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: NewsArticleDao
) {

    fun getTopHeadlines() =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(api = apiInterface) }
        ).flow



}