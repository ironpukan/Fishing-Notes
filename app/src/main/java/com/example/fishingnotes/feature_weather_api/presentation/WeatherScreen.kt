package com.example.fishingnotes.feature_weather_api.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fishingnotes.feature_weather_api.presentation.components.WeatherCard
import com.example.fishingnotes.feature_weather_api.presentation.components.WeatherForecast
import com.example.fishingnotes.ui.theme.PurpleGrey80

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    state: WeatherState
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleGrey80)
        ) {
            WeatherCard(
                state = state,
                colors = CardDefaults.cardColors()
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(state = state)

        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
        state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}