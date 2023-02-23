package com.yotfr.testapp.ui.screens.cameras.event

sealed interface CamerasEvent {
    object Swiped : CamerasEvent
}