package com.capstone.agroai.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")

    object Detail: Screen("detail")

    object History: Screen("history")

    object Profile: Screen("profile")
}