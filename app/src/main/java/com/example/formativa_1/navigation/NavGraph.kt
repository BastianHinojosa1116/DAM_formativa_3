package com.example.formativa_1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.formativa_1.data.UserDataStore

import com.example.formativa_1.screens.LoginScreen
import com.example.formativa_1.screens.RecoverPasswordScreen
import com.example.formativa_1.screens.RegisterScreen
import com.example.formativa_1.screens.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val userPrefs = UserDataStore(context)

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigate = { route -> navController.navigate(route) },
                userPrefs = userPrefs
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigate = { route -> navController.navigate(route) },
                userPrefs = userPrefs
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                userPrefs = userPrefs


            )
        }
        composable(Screen.RecoverPassword.route) {
            RecoverPasswordScreen(
                onNavigate = { route -> navController.navigate(route) },
                userPrefs = userPrefs
            )
        }


    }
}