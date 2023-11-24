package com.example.fishingnotes.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Maps : BottomNavigationScreens(
        title = "Maps",
        route = Screen.MapsScreen.route,
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map
    )

    object Notes : BottomNavigationScreens(
        title = "Notes",
        route = Screen.NotesScreen.route,
        selectedIcon = Icons.Filled.Notes,
        unselectedIcon = Icons.Outlined.Notes
    )

    object Weather : BottomNavigationScreens(
        title = "Weather",
        route = Screen.WeatherScreen.route,
        selectedIcon = Icons.Filled.WbSunny,
        unselectedIcon = Icons.Outlined.WbSunny
    )
}