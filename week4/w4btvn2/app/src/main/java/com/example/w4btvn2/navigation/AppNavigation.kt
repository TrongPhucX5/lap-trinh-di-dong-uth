package com.example.w4btvn2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.w4btvn2.ui.screens.ConfirmScreen
import com.example.w4btvn2.ui.screens.CreateNewPasswordScreen
import com.example.w4btvn2.ui.screens.ForgotPasswordScreen
import com.example.w4btvn2.ui.screens.VerificationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "forgot_password_screen") {
        composable("forgot_password_screen") {
            ForgotPasswordScreen(navController = navController)
        }
        composable(
            route = "verification_screen/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            VerificationScreen(navController = navController, email = email)
        }
        composable(
            route = "createNewPassword_screen/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            CreateNewPasswordScreen(navController = navController, email = email)
        }
        composable(
            route = "confirm_screen/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val password = backStackEntry.arguments?.getString("password")
            ConfirmScreen(navController = navController, email = email, password = password)
        }
    }
}