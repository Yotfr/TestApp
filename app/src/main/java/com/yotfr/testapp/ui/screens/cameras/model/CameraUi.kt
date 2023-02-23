package com.yotfr.testapp.ui.screens.cameras.model

data class CameraUi(
    val id: Int,
    val name: String,
    val rec: Boolean,
    val room: String?,
    val snapshot: String
)
