package com.yotfr.testapp.ui.screens.doors.model

data class DoorUi(
    val id: Int,
    val favorites: Boolean,
    val name: String,
    val room: String?,
    val snapshot: String?,
    val isOpen: Boolean
)
