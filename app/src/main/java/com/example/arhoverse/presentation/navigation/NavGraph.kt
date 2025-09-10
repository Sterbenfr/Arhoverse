package com.example.arhoverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arhoverse.presentation.user.UserDetailScreen
import com.example.arhoverse.presentation.user.UserDetailViewModel

sealed class Screen(val route: String) {
    object UserDetail : Screen("userDetail/{userId}") {
        fun createRoute(userId: Int) = "userDetail/$userId"
    }
}

@Composable
fun AppNavGraph(
    viewModelFactory: (Int) -> UserDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.UserDetail.createRoute(1)) {
        composable("userDetail/{userId}") { backStackEntry ->
            val userId = 1 // Test toujours sur l'utilisateur 1
            val viewModel = viewModelFactory(userId)
            UserDetailScreen(userId = userId, viewModel = viewModel)
        }
    }
}
