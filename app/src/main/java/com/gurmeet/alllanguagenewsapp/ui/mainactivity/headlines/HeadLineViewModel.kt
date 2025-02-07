package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.COUNTRY
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.DEBOUNCE_TIMEOUT
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.MIN_SEARCH_CHAR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

import androidx.paging.cachedIn
import kotlinx.coroutines.flow.onStart


class HeadLineViewModel (private val topHeadlineRepository: TopHeadlineRepository):ViewModel() {


    // UI State flow for managing loading, success, and error states
    private val _uiState2 = MutableStateFlow<UiState<PagingData<Any>>>(UiState.Loading)
    val uiState2: StateFlow<UiState<PagingData<Any>>> = _uiState2



    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState
    private val _selectedItem = MutableLiveData<String>()



    private val query = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    fun searchNews(searchQuery: String) {
        query.value = searchQuery
    }



     fun createNewsFlow() {
        viewModelScope.launch {
            query.debounce(DEBOUNCE_TIMEOUT)
                .filter {
                    if (it.isNotEmpty() && it.length >= MIN_SEARCH_CHAR) {
                        return@filter true
                    } else {
                        _uiState.value = UiState.Success(emptyList())
                        return@filter false
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    _uiState.value = UiState.Loading
                    return@flatMapLatest topHeadlineRepository.getNews(it)
                        .catch { e ->
                            // handle error properly
                            _uiState.value = UiState.Error(e.toString())
                        }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    // handle response and empty response properly
                    _uiState.value = UiState.Success(it)
                }}}

   /* public fun fetchNews() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(COUNTRY)
                .cachedIn(viewModelScope) // Cache data in viewModelScope
                .onStart { _uiState2.value = UiState.Loading } // Show loading state
                .catch { e -> _uiState2.value = UiState.Error(e.message ?: "Unknown error") } // Handle errors
                .collect { pagingData ->
                    _uiState2.value = UiState.Success(pagingData) // Show success with data
                }
        }
    }*/


    fun fetchPagedData(
        screenType: String,
        country: String?,
        additionalParam: String? = null
    ) {
        viewModelScope.launch {
            topHeadlineRepository.getPagedData(screenType, country, additionalParam)
                .cachedIn(viewModelScope) // Cache data in viewModelScope
                .onStart { _uiState2.value = UiState.Loading } // Show loading state
                .catch { e -> _uiState2.value = UiState.Error(e.message ?: "Unknown error") } // Handle errors
                .collect { pagingData ->
                    _uiState2.value = UiState.Success(pagingData) // Show success with data
                }
        }
    }









     fun fetchNewsDetail( id:String) {
        viewModelScope.launch {
            topHeadlineRepository.getSourceDetails(id)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
    fun fetchTeslaArticles() {
        viewModelScope.launch {
            topHeadlineRepository.getTeslaArticles()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }


}