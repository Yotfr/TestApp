package com.yotfr.testapp.ui.screens.doors.model

data class DoorsState(
    val isLoading: Boolean = false,
    val doors: List<DoorUi> = emptyList()
)
