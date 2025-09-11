package com.example.arhoverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arhoverse.presentation.user.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object UserList : Screen("userList")
    object UserDetail : Screen("userDetail/{userId}") {
        fun createRoute(userId: Int) = "userDetail/$userId"
    }
}

@Composable
fun AppNavGraph(
    userListViewModelFactory: () -> UserListViewModel,
    userDetailViewModelFactory: (Int) -> UserDetailViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.UserList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.UserList.route) {
            val viewModel = remember { userListViewModelFactory() }
            UserListScreen(
                viewModel = viewModel,
                onUserClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                }
            )
        }

        composable(Screen.UserDetail.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 1
            val viewModel = remember(userId) { userDetailViewModelFactory(userId) }
            UserDetailScreen(
                userId = userId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
