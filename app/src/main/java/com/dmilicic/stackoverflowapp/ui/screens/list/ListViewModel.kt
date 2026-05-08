package com.dmilicic.stackoverflowapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmilicic.stackoverflowapp.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiModel.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadTopUsers()
    }

    fun loadTopUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val users = userRepository.getTopUsers()
            _uiState.value = _uiState.value.copy(users = users, isLoading = false)
        }
    }
}