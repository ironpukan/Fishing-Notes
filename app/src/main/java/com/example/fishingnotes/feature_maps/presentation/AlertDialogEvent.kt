package com.example.fishingnotes.feature_maps.presentation

sealed class AlertDialogEvent {
    object OnDismissClick : AlertDialogEvent()
    object OnConfirmationClick : AlertDialogEvent()
}
