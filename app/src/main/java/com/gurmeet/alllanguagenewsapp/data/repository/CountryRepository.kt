package com.gurmeet.alllanguagenewsapp.data.repository


import com.gurmeet.alllanguagenewsapp.data.model.country.Country
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor() {


    fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(AppConstant.COUNTRIES)
        }
    }

    fun getLanguages(): Flow<List<Country>> {
        return flow {
            emit(AppConstant.LANGUAGES)
        }
    }

}