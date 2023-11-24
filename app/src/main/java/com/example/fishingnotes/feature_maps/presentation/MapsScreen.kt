package com.example.fishingnotes.feature_maps.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fishingnotes.feature_maps.presentation.components.MarkerInfo
import com.example.fishingnotes.feature_maps.presentation.components.SmallNoteItem
import com.example.fishingnotes.util.Screen
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MapsViewModel = hiltViewModel()
) {

    val latLngState = viewModel.latLng.value
    val titleState = viewModel.markerTitle.value
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MapsViewModel.UiEvent.MapLongClicked,
                is MapsViewModel.UiEvent.MarkerClicked -> {
                    scaffoldState.bottomSheetState.expand()
                }

                is MapsViewModel.UiEvent.MapClicked,
                is MapsViewModel.UiEvent.SaveMarker -> {
                    scaffoldState.bottomSheetState.hide()

                }

                is MapsViewModel.UiEvent.DeleteMarker -> {
                    openAlertDialog.value = true
                }

                is MapsViewModel.UiEvent.OnConfirmationClick -> {
                    openAlertDialog.value = false
                    scaffoldState.bottomSheetState.hide()
                }

                is MapsViewModel.UiEvent.OnDismissClick -> {
                    openAlertDialog.value = false
                }

                is MapsViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                else -> {
                    // TODO:
                }
            }
        }
    }

    //    49.80939 30.11209

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
                    .clip(
                        CutCornerShape(
                            bottomEnd = 16.dp,
                            bottomStart = 16.dp
                        )
                    )
            ) {
                MarkerInfo(
                    savedMarker = state.savedMarker,
                    titleState = titleState,
                    onValueChange = { viewModel.onMarkerEvent(MarkerEvent.EnteredTitle(it)) },
                    onFocusChange = { viewModel.onMarkerEvent(MarkerEvent.ChangeTitleFocus(it)) },
                    onAddNoteClick = {
                        navController.navigate(
                            Screen.AddEditNoteScreen.route.plus(
                                "?noteId=${-1}&markerId=${state.currentMarker?.id}"
                            )
                        )
                    },
                    onAddMarkerClick = { viewModel.onMarkerEvent(MarkerEvent.SaveMarker) },
                    onDeleteMarkerClick = { viewModel.onMarkerEvent(MarkerEvent.DeleteMarker) }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                state.markerWithNotes?.let {
                    items(it.notes) { note ->
                        Spacer(modifier = Modifier.height(8.dp))
                        SmallNoteItem(
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route.plus(
                                        "?noteId=${note.id}&markerId=${it.marker.id}"
                                    )
                                )
                            },
                            note = note
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = 0.dp,
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
        },
        sheetSwipeEnabled = true,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        GoogleMap(
            onMapLongClick = { latLng ->
                viewModel.onMapEvent(MapsEvent.MapLongClicked(latLng))
            },
            onMapClick = {
                viewModel.onMapEvent(MapsEvent.MapClicked)
            },
            modifier = modifier.padding(it),
            properties = MapProperties(mapType = MapType.SATELLITE)
        ) {
            Marker(
                visible = state.visible,
                state = MarkerState(
                    position = latLngState
                ),
                onClick = { marker ->
                    viewModel.onMarkerEvent(MarkerEvent.UnsavedMarkerClicked)
                    true
                }
            )

            state.markers.onEach { marker ->
                Marker(
                    visible = true,
                    state = MarkerState(
                        position = LatLng(
                            marker.latitude,
                            marker.longitude
                        )
                    ),
                    onClick = {
                        viewModel.onMarkerEvent(MarkerEvent.MarkerClicked(marker))
                        true
                    }
                )
            }
        }


        if (openAlertDialog.value) {
            AlertDialog(
                icon = {
                    Icon(imageVector = Icons.Default.Dangerous, contentDescription = "Alert icon")
                },
                title = {
                    Text(text = "Delete the marker?")
                },
                text = {
                    Text(text = "All related notes will be also deleted!")
                },
                onDismissRequest = {
                    openAlertDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onAlertDialogEvent(AlertDialogEvent.OnConfirmationClick)
                        }
                    ) {
                        Text("Delete anyway")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            Log.d("ALERT", "hide")
                            viewModel.onAlertDialogEvent(AlertDialogEvent.OnDismissClick)
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}
