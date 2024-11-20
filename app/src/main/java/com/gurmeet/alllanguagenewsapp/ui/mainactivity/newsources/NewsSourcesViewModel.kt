package com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurmeet.alllanguagenewsapp.data.repository.NewsSourceRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagesapp.NewsSources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourcesViewModel(private val topSourceRepository: NewsSourceRepository):ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<NewsSources>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<NewsSources>>> = _uiState
    // LiveData to hold the selected item data

  /*  init {
        fetchTopSourceList()
    }*/

     fun fetchTopSourceList() {
        viewModelScope.launch {
            topSourceRepository.getTopSource()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}