package com.yotfr.testapp.ui.screens.cameras.model

data class CamerasState(
    val isLoading: Boolean = false,
    val rooms: List<Room> = emptyList()
)
