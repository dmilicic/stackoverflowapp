package com.dmilicic.stackoverflowapp.ui.screens.list

import com.dmilicic.stackoverflowapp.models.UserModel

data class ListUiModel(
    val users: List<UserModel> = emptyList(),
    val followedUsers: List<Int> = emptyList(),
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
) {
    companion object {
        val Initial = ListUiModel()
    }
}