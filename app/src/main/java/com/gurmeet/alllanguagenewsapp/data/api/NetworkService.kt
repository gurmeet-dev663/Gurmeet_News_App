package com.gurmeet.alllanguagenewsapp.data.api

import com.gurmeet.alllanguagenewsapp.data.model.TopHeadLinesResponse
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadLinesResponse
}