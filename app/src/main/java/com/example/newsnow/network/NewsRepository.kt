package com.example.newsnow.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsnow.paging.NewsPagingSource
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getTopHeadlines() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 80,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(api = apiInterface) }
        ).flow

}