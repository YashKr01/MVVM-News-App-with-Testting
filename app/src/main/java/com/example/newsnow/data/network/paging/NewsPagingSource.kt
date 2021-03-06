package com.example.newsnow.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.database.NewsArticleDao
import com.example.newsnow.data.network.retrofit.ApiInterface
import com.example.newsnow.utils.Constants.BREAKING
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val api: ApiInterface,
    private val dao: NewsArticleDao,
    private val query: String
) : PagingSource<Int, NewsArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = if (query == BREAKING) api.getTopHeadlines(
                position,
                params.loadSize
            ) else api.searchNews(query, position, params.loadSize)

            val newsList = response.articleDto

            val databaseList = dao.getList().first()
            val newsArticles = newsList.map { newsArticle ->

                val isBookMarked = databaseList.any { bookmarked ->
                    bookmarked.url == newsArticle.url
                }

                NewsArticle(
                    title = newsArticle.title,
                    url = newsArticle.url,
                    urlToImage = newsArticle.urlToImage,
                    description = newsArticle.description,
                    publishedAt = newsArticle.publishedAt,
                    isBookmarked = isBookMarked
                )

            }

            LoadResult.Page(
                data = newsArticles,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (newsList.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? = null

}