package com.gurmeet.alllanguagenewsapp.data.repository

import androidx.annotation.Keep
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines.Article
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Keep
@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
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