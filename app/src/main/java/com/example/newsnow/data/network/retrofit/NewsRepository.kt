package com.example.newsnow.data.network.retrofit

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.database.NewsArticleDao
import com.example.newsnow.data.network.paging.NewsPagingSource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: NewsArticleDao,
) {

    fun getTopHeadlines(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { NewsPagingSource(api = apiInterface, dao = dao, query) }
        ).liveData

    suspend fun insertArticle(newsArticle: NewsArticle) = dao.insertArticle(newsArticle)

    suspend fun deleteArticle(newsArticle: NewsArticle) = dao.deleteArticle(newsArticle)

    fun getSavedNews() = dao.getList()

    suspend fun deleteAllNews() = dao.deleteAllNews()

}