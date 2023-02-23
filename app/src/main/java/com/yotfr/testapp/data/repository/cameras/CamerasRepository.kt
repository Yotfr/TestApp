package com.yotfr.testapp.data.repository.cameras

import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.data.util.Response
import kotlinx.coroutines.flow.Flow

interface CamerasRepository {
    fun getCamerasData(): Flow<Response<CamerasDataRealm>>
}