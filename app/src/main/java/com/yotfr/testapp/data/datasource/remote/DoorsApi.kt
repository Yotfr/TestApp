package com.yotfr.testapp.data.datasource.remote

import com.yotfr.testapp.data.datasource.remote.dto.doors.DoorsDto
import retrofit2.http.GET

interface DoorsApi {

    @GET("api/rubetek/doors/")
    suspend fun fetchDoors(): DoorsDto
}
