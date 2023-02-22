package com.yotfr.testapp.data.datasource.remote

import com.yotfr.testapp.data.datasource.remote.dto.cameras.CamerasDto
import retrofit2.http.GET

interface CamerasApi {

    @GET("api/rubetek/cameras/")
    suspend fun fetchCameras(): CamerasDto
}
