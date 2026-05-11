package com.dmilicic.stackoverflowapp.ui.navigation

sealed class Screen(val route: String) {
    object List: Screen("list_screen")
}