package com.yotfr.testapp.data.datasource.remote.dto.doors

data class DoorsDataDto(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val room: String,
    val snapshot: String
)