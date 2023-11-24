package com.example.fishingnotes.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fishingnotes.feature_maps.presentation.MapsScreen
import com.example.fishingnotes.feature_notes.presentation.add_edit_note.AddEditNoteScreen
import com.example.fishingnotes.feature_notes.presentation.notes.NotesScreen
import com.example.fishingnotes.feature_weather_api.presentation.DailyWeatherScreen

@Composable
fun MainScreenNavigationConfigurations(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MapsScreen.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&markerId={markerId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "markerId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            //val markerId = it.arguments?.getInt("markerId") ?: -1
            AddEditNoteScreen(navController = navController)
        }
        composable(route = Screen.WeatherScreen.route) {
            DailyWeatherScreen(navController = navController)
        }
        composable(route = Screen.MapsScreen.route) {
            MapsScreen(navController = navController)
        }
    }
}