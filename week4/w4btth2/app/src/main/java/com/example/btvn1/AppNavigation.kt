package com.example.btvn1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding/{pageIndex}"
    const val HOME = "home"

    fun onboarding(pageIndex: Int) = "onboarding/$pageIndex"
}

@Composable
fun SplashScreen(navController: NavController) {
    // Phần logic này giữ nguyên, không thay đổi
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Routes.onboarding(0)) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    // Giao diện sẽ được thay đổi ở đây
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Căn giữa Column trong Box
    ) {
        // Dùng Column để xếp các phần tử theo chiều dọc
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 1. Thêm hình ảnh logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp) // Bạn có thể điều chỉnh kích thước logo ở đây
            )

            // Thêm khoảng cách giữa logo và chữ
            Spacer(modifier = Modifier.height(16.dp))

            // 2. Thêm dòng chữ
            Text(
                text = "UTH SmartTasks",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Đây là Màn hình chính", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Routes.ONBOARDING,
            arguments = listOf(navArgument("pageIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val pageIndex = backStackEntry.arguments?.getInt("pageIndex") ?: 0
            OnboardingScreen(navController = navController, pageIndex = pageIndex)
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}