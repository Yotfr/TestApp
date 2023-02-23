package com.yotfr.testapp.data.repository.doors

import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import com.yotfr.testapp.data.util.Response
import kotlinx.coroutines.flow.Flow

interface DoorsRepository {
    fun getDoorsData(forceUpdate: Boolean): Flow<Response<DoorsDataRealm>>
    suspend fun updateDoor(doorRealm: DoorRealm)
}