package com.gurmeet.alllanguagenewsapp.data.repository

import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.language.News
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepository @Inject  constructor(@BaseUrl2 private val networkService: NetworkService) {

    fun getAllLanguageRepo(language: String): Flow<List<News>> {
      return flow{
          emit(networkService.getAllLanguageResponse(language,AppConstant.API_KEY_ALL_LANGUAGE))
      }.map{
          it.news
      }
    }

    fun getAllLanguageRepoCountry(country: String): Flow<List<News>> {
        return flow{
            emit(networkService.getAllLanguageResponseCountry(country,AppConstant.API_KEY_ALL_LANGUAGE))
        }.map{
            it.news
        }
    }
}