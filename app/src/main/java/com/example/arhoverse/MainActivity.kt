package com.example.arhoverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.data.repository.BookmarkRepository
import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.data.repository.PostRepository
import com.example.arhoverse.data.repository.UserRepository
import com.example.arhoverse.domain.usecase.GetPostCommentsUseCase
import com.example.arhoverse.domain.usecase.GetPostLikesUseCase
import com.example.arhoverse.domain.usecase.GetPostUseCase
import com.example.arhoverse.domain.usecase.GetUserBookmarksUseCase
import com.example.arhoverse.domain.usecase.GetUserPostsUseCase
import com.example.arhoverse.domain.usecase.GetUserUseCase
import com.example.arhoverse.domain.usecase.GetUsersUseCase
import com.example.arhoverse.presentation.navigation.AppNavGraph
import com.example.arhoverse.presentation.post.PostDetailViewModel
import com.example.arhoverse.presentation.user.UserDetailViewModel
import com.example.arhoverse.presentation.user.UserListViewModel
import com.example.arhoverse.presentation.feed.theme.ArhoverseTheme
import com.example.arhoverse.presentation.feed.FeedViewModel
import com.example.arhoverse.presentation.feed.FeedViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mini-social-api-ilyl.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val userRepository = UserRepository(apiService)
        val getUserUseCase = GetUserUseCase(userRepository)
        val postRepository = PostRepository(apiService)
        val followRepository = com.example.arhoverse.data.repository.FollowRepository(apiService)
        val getFollowersUseCase = com.example.arhoverse.domain.usecase.GetFollowersUseCase(followRepository)
        val followUserUseCase = com.example.arhoverse.domain.usecase.FollowUserUseCase(followRepository)
        val unfollowUserUseCase = com.example.arhoverse.domain.usecase.UnfollowUserUseCase(followRepository)
        val getUserPostsUseCase = GetUserPostsUseCase(postRepository)
        val getUsersUseCase = GetUsersUseCase(userRepository)
        val bookmarkRepository = BookmarkRepository(apiService)
        val getPostUseCase = GetPostUseCase(postRepository)
        val getPostCommentsUseCase = GetPostCommentsUseCase(postRepository)
        val getPostLikesUseCase = GetPostLikesUseCase(postRepository)
        val getUserBookmarksUseCase = GetUserBookmarksUseCase(bookmarkRepository)
        val feedRepository = FeedRepository(apiService)
        val likePostUseCase = com.example.arhoverse.domain.usecase.LikePostUseCase(feedRepository)
        val unlikePostUseCase = com.example.arhoverse.domain.usecase.UnlikePostUseCase(feedRepository)
        val addBookmarkUseCase = com.example.arhoverse.domain.usecase.AddBookmarkUseCase(feedRepository)
        val removeBookmarkUseCase = com.example.arhoverse.domain.usecase.RemoveBookmarkUseCase(feedRepository)
        val getPostBookmarksUseCase = com.example.arhoverse.domain.usecase.GetPostBookmarksUseCase(feedRepository)
        setContent {
            ArhoverseTheme {
                AppNavGraph(
                    userListViewModelFactory = {
                        UserListViewModel(getUsersUseCase)
                    },
                    userDetailViewModelFactory = { userId ->
                        UserDetailViewModel(
                            getUserUseCase,
                            getUserPostsUseCase,
                            getFollowersUseCase,
                            followUserUseCase,
                            unfollowUserUseCase
                        )
                    },
                    postDetailViewModelFactory = { postId ->
                        PostDetailViewModel(
                            getPostUseCase,
                            getUserUseCase,
                            getPostCommentsUseCase,
                            getPostLikesUseCase,
                            getUserBookmarksUseCase,
                            likePostUseCase,
                            unlikePostUseCase,
                            addBookmarkUseCase,
                            removeBookmarkUseCase,
                            getPostBookmarksUseCase,
                            com.example.arhoverse.domain.usecase.AddCommentUseCase(feedRepository)
                        )
                    },
                    feedViewModelFactory = {
                        FeedViewModelFactory(feedRepository)
                    }
                )
            }
        }
    }
}
