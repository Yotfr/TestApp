package com.yotfr.testapp.ui.screens.cameras.model

data class CamerasState(
    val isLoading: Boolean = false,
    val rooms: Map<String?, List<CameraUi>> = emptyMap()
)
