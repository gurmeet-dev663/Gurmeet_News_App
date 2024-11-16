package com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines.Article
import com.gurmeet.alllanguagenewsapp.data.repository.TopSourceRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.utils.AppConstant
import com.gurmeet.alllanguagesapp.NewsSources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopSourcesViewModel(private val topSourceRepository: TopSourceRepository):ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<NewsSources>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<NewsSources>>> = _uiState
    // LiveData to hold the selected item data

    init {
        fetchTopSourceList()
    }

    private fun fetchTopSourceList() {
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