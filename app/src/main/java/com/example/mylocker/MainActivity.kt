package com.example.mylocker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mylocker.ui.theme.MyLockerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLockerTheme {
                App()

            }
        }
    }
}

@Composable
fun App() {
    // NavController pour gérer la navigation
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Route pour l'écran de connexion
        composable("login") {
            LoginScreen(onLoginSuccess = {
                // Naviguer vers l'écran des casiers après une connexion réussie
                navController.navigate("lockers")
            })
        }

        // Route pour l'écran des casiers
        composable("lockers") {
            LockerGridScreen()
        }
    }
}