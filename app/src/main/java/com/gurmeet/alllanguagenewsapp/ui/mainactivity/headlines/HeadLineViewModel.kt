package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.COUNTRY
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.DEBOUNCE_TIMEOUT
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.MIN_SEARCH_CHAR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HeadLineViewModel (private val topHeadlineRepository: TopHeadlineRepository):ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState
    private val _selectedItem = MutableLiveData<String>()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages

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
                }
        }
    }










    fun loadNextPage() {
        _isLoading.value = true
        val currentPage = _currentPage.value
        if (currentPage < _totalPages.value) {
            _currentPage.value = currentPage + 1
            fetchNews()
        }
    }

    // Call this function to load the previous page
    fun loadPreviousPage() {
        val currentPage = _currentPage.value
        if (currentPage > 1) {
            _currentPage.value = currentPage - 1
            fetchNews()
        }
    }

     fun fetchNews() {
         val page = _currentPage.value ?: 1
         val pageSize = 20  // Or a variable depending on your pagination needs
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(COUNTRY,_currentPage.value, 5)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                    _totalPages.value = 37
                    _isLoading.value = false

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