package com.yotfr.testapp.ui.screens.doors.event

import com.yotfr.testapp.ui.screens.doors.model.DoorUi

sealed interface DoorsEvent {
    object Swiped : DoorsEvent
    data class FavoriteClicked(val door: DoorUi) : DoorsEvent
    data class EditNameClicked(val door: DoorUi, val newName: String) : DoorsEvent
}
