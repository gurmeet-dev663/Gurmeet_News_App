package com.gurmeet.alllanguagenewsapp.data.repository

import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import com.gurmeet.alllanguagesapp.NewsSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourceRepository @Inject constructor(private val networkService: NetworkService){

    fun getTopSource(): Flow<List<NewsSources>>{
        return flow {
            emit(networkService.getTopHeadlinesSources(AppConstant.API_KEY))
        }.map {
            it.sources
        }
    }
}