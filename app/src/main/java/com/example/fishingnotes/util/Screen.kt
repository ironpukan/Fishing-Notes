package com.example.fishingnotes.util

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
    object WeatherScreen : Screen("weather_screen")
    object MapsScreen : Screen("maps_screen")
}
