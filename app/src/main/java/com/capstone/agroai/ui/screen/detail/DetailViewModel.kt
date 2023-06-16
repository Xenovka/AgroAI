package com.capstone.agroai.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.agroai.data.Repository
import com.capstone.agroai.model.Classification
import com.capstone.agroai.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Classification>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Classification>>
        get() = _uiState

    fun getClassificationByName(diseaseName: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getClassificationByName(diseaseName))
        }
    }
}