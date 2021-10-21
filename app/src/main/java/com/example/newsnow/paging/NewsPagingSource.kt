package com.example.newsnow.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.database.NewsArticleDao
import com.example.newsnow.network.ApiInterface
import com.example.newsnow.network.model.NewsArticleDto
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource(private val api: ApiInterface) : PagingSource<Int, NewsArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticleDto> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getTopHeadlines(position, params.loadSize)
            val newsList = response.articleDto

            LoadResult.Page(
                data = newsList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (newsList.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticleDto>): Int? = null

}