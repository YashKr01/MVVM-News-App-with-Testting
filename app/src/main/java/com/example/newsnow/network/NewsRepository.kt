package com.example.newsnow.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsnow.database.NewsArticle
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
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { NewsPagingSource(api = apiInterface, dao = dao) }
        ).flow

    suspend fun insertArticle(newsArticle: NewsArticle) = dao.insertArticle(newsArticle)

    suspend fun deleteArticle(newsArticle: NewsArticle) = dao.deleteArticle(newsArticle)

    fun getSavedNews() = dao.getList()

    suspend fun deleteAllNews() = dao.deleteAllNews()

}