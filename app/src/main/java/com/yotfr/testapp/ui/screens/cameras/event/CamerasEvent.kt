package com.yotfr.testapp.ui.screens.cameras.event

import com.yotfr.testapp.ui.screens.cameras.model.CameraUi

sealed interface CamerasEvent {
    object Swiped : CamerasEvent
    data class FavoriteClicked(val camera: CameraUi) : CamerasEvent
}