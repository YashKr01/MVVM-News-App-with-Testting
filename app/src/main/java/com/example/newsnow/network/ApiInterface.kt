package com.example.newsnow.network

import com.example.newsnow.network.model.NewsResponse
import com.example.newsnow.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

    @GET("everything?apiKey=$API_KEY")
    suspend fun searchNews(
        @Query("q") query: String,
    ): NewsResponse

}