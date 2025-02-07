package com.gurmeet.alllanguagenewsapp.data.repository


import DynamicPagingSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import com.gurmeet.alllanguagenewsapp.utils.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

 /*   fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country,0,1))
        }.map {
            it.articles
        }
    }*/


    fun getPagedData(
        screenType: String,
        country: String?,
        additionalParam: String? = null
    ) = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 1,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DynamicPagingSource(
                apiService = networkService,
                screenType = screenType,
                country = country,
                additionalParam = additionalParam
            )
        }
    ).flow

    fun getTopHeadlines(country: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1,

                // Define number of items per page
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingSource(networkService, country) }
        ).flow
            .distinctUntilChanged() // ✅ Prevents multiple API calls with the same data
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