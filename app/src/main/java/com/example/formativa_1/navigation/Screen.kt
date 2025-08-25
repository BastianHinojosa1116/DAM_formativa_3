package com.example.formativa_1.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object RecoverPassword : Screen("recover_password")
    object Home : Screen (route = "home")

        fun createRoute(itemId: String) = "detail/$itemId"
    }

