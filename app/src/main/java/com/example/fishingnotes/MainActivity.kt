package com.example.fishingnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.fishingnotes.ui.theme.FishingNotesTheme
import com.example.fishingnotes.util.AppBottomNavigation
import com.example.fishingnotes.util.BottomNavigationScreens
import com.example.fishingnotes.util.MainScreenNavigationConfigurations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FishingNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Maps,
        BottomNavigationScreens.Notes,
        BottomNavigationScreens.Weather
    )
    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController, items = bottomNavigationItems)
        }
    ) {
        MainScreenNavigationConfigurations(paddingValues = it, navController = navController)
    }
}