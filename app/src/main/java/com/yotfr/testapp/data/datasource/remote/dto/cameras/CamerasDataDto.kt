package com.yotfr.testapp.data.datasource.remote.dto.cameras

import com.squareup.moshi.Json

data class CamerasDataDto(
    @field:Json(name = "cameras")
    val cameraDtos: List<CameraDto>,
    val room: List<String>
)