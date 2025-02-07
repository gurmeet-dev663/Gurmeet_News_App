package com.gurmeet.alllanguagenewsapp.ui.base

sealed interface UiState2<out T> {

   /* data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>




    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: PagingData<Article>) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()*/
}