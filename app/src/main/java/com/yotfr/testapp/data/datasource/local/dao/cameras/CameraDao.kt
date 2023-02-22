package com.yotfr.testapp.data.datasource.local.dao.cameras

import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CameraDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertCamerasData(camerasData: CamerasData) {
        realm.write { copyToRealm(camerasData) }
    }
    suspend fun deleteCamerasData(camerasData: CamerasData) {
        realm.write {
            val camerasDataQuery = query<CamerasData>().find()
            delete(camerasDataQuery)
        }
    }
    fun getCamerasData(): Flow<List<CamerasData>> {
        return realm.query<CamerasData>().asFlow().map { it.list }
    }
}