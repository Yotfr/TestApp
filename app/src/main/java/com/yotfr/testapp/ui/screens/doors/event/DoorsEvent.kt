package com.yotfr.testapp.ui.screens.doors.event

sealed interface DoorsEvent {
    object Swiped : DoorsEvent
}