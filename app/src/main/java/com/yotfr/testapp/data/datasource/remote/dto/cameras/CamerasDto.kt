package com.yotfr.testapp.data.datasource.remote.dto.cameras

import com.squareup.moshi.Json

data class CamerasDto(
    @field:Json(name = "data")
    val camerasDataDTO: CamerasDataDto
)