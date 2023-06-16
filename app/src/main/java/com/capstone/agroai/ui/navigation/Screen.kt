package com.capstone.agroai.ui.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")

    object Home: Screen("home")

    object Detail: Screen("detail/{disease}/{imageUri}")

//    object History: Screen("history")
//
//    object Profile: Screen("profile")
}