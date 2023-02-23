package com.yotfr.testapp.ui.screens.cameras

sealed interface CamerasEvent {
    object Swiped : CamerasEvent
}