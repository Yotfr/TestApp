package com.yotfr.testapp.data.datasource.remote.dto.doors

import com.squareup.moshi.Json

data class DoorsDto(
    @field:Json(name = "data")
    val data: List<DoorsDataDto>
)