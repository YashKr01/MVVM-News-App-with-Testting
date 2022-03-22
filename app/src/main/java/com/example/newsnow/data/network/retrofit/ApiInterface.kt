package com.example.newsnow.data.network.retrofit

import com.example.newsnow.data.network.model.NewsResponse
import com.example.newsnow.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines?country=in&apiKey=$API_KEY")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

}