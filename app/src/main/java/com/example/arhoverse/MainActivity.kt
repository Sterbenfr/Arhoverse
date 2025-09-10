package com.example.arhoverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.data.repository.UserRepository
import com.example.arhoverse.domain.usecase.GetUserUseCase
import com.example.arhoverse.presentation.navigation.AppNavGraph
import com.example.arhoverse.presentation.user.UserDetailViewModel
import com.example.arhoverse.ui.theme.ArhoverseTheme
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
        setContent {
            ArhoverseTheme {
                AppNavGraph(
                    viewModelFactory = { userId ->
                        UserDetailViewModel(getUserUseCase)
                    }
                )
            }
        }
    }
}
