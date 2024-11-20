package com.gurmeet.alllanguagenewsapp.ui.mainactivity.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurmeet.alllanguagenewsapp.data.model.language.News
import com.gurmeet.alllanguagenewsapp.data.repository.AllLanguageRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopLanguageViewModel (private val allLanguageRepository: AllLanguageRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<News>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<News>>> = _uiState
    // LiveData to hold the selected item data


     fun fetchAllLanguage(language:String) {
        viewModelScope.launch {
            allLanguageRepository.getAllLanguageRepo(language)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }}
    fun fetchAllLanguageCountry(country:String) {
        viewModelScope.launch {
            allLanguageRepository.getAllLanguageRepoCountry(country)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }}
}