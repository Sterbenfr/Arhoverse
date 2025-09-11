package com.example.arhoverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arhoverse.presentation.user.UserDetailScreen
import com.example.arhoverse.presentation.user.UserDetailViewModel
import com.example.arhoverse.presentation.user.UserListScreen
import com.example.arhoverse.presentation.user.UserListViewModel
import com.example.arhoverse.presentation.post.PostDetailScreen
import com.example.arhoverse.presentation.post.PostDetailViewModel
import androidx.compose.runtime.remember
import com.example.arhoverse.presentation.feed.feed.FeedScreen
import com.example.arhoverse.presentation.feed.FeedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider

sealed class Screen(val route: String) {
    object UserList : Screen("userList")
    object UserDetail : Screen("userDetail/{userId}") {
        fun createRoute(userId: Int) = "userDetail/$userId"
    }
    object PostDetail : Screen("postDetail/{postId}") {
        fun createRoute(postId: Int) = "postDetail/$postId"
    }
    object Feed : Screen("feed")
}

@Composable
fun AppNavGraph(
    userListViewModelFactory: () -> UserListViewModel,
    userDetailViewModelFactory: (Int) -> UserDetailViewModel,
    postDetailViewModelFactory: (Int) -> PostDetailViewModel,
    feedViewModelFactory: () -> ViewModelProvider.Factory
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Feed.route) {
        composable(Screen.Feed.route) {
            val viewModel: FeedViewModel = viewModel(factory = feedViewModelFactory())
            FeedScreen(
                viewModel = viewModel,
                onPostClick = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId.toInt()))
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
        composable("userDetail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 1
            val viewModel = remember(userId) { userDetailViewModelFactory(userId) }
            UserDetailScreen(
                userId = userId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPostClick = { postId -> navController.navigate(Screen.PostDetail.createRoute(postId)) }
            )
        }
        composable("postDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: return@composable
            val viewModel = remember(postId) { postDetailViewModelFactory(postId) }
            PostDetailScreen(
                postId = postId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
