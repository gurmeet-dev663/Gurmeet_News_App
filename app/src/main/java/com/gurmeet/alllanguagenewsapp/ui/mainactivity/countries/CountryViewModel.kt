package com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurmeet.alllanguagenewsapp.data.model.model.country.Country
import com.gurmeet.alllanguagenewsapp.data.repository.SearchRepository
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryViewModel (private val countriesRepository: SearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Country>>> = _uiState



     fun fetchCountries() {
        viewModelScope.launch {
            countriesRepository.getCountries()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

     fun fetchLanguages() {
        viewModelScope.launch {
            countriesRepository.getLanguages()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}