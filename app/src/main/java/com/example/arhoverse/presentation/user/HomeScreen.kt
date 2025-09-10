package com.example.arhoverse.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Bienvenue dans le Home", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("userDetail/1") }) {
            Text("Voir les détails de l'utilisateur 1")
        }

        Button(onClick = { navController.navigate("userDetail/2") }) {
            Text("Voir les détails de l'utilisateur 2")
        }
    }
}
