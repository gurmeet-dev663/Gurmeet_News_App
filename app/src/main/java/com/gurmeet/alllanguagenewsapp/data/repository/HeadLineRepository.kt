package com.gurmeet.alllanguagenewsapp.data.repository

import android.util.Log
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }.retry(3) { e ->
            // Retry condition


            delay(1000) // Optional delay before retry
            e is RuntimeException
        }
            .catch { e ->
                // Handle failure after retries are exhausted

            }
    }
    fun getNews(query: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNews(query))
        }.map {
            it.articles
        }
    }

    fun getSourceDetails(id: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlinesDetails(id, AppConstant.API_KEY))
        }.map {
            it.articles
        }
    }

    fun getTeslaArticles(): Flow<List<Article>> {
        return flow {
            emit(networkService.getTeslaArticles(AppConstant.TESLA,AppConstant.PUBLISHEDAT, AppConstant.API_KEY))
        }.map {
            it.articles
        }
    }

}