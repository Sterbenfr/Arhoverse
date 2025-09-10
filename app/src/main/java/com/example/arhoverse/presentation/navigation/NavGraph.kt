package com.example.arhoverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arhoverse.presentation.user.*
import com.example.arhoverse.domain.usecase.GetUserUseCase
import com.example.arhoverse.data.repository.UserRepository


@Composable
fun NavGraph(navController: NavHostController, getUserUseCase: GetUserUseCase) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                authViewModel = viewModel(),
                onLoginSuccess = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("userDetail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            val userDetailViewModel: UserDetailViewModel = viewModel(
                factory = UserDetailViewModelFactory(getUserUseCase)
            )
            UserDetailScreen(userId = userId, viewModel = userDetailViewModel)
        }
    }
}
