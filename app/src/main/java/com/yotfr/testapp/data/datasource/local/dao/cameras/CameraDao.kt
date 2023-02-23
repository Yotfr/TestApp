package com.yotfr.testapp.data.datasource.local.dao.cameras

import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CameraDao(
    private val realm: Realm
) {
    suspend fun insertCamerasData(camerasDataRealm: CamerasDataRealm) {
        realm.write { copyToRealm(camerasDataRealm) }
    }
    suspend fun deleteCamerasData() {
        realm.write {
            val camerasDataRealmQuery = query<CamerasDataRealm>().find()
            val camerasRealmQuery = query<CameraRealm>().find()
            delete(camerasDataRealmQuery)
            delete(camerasRealmQuery)
        }
    }

    suspend fun updateCamera(cameraRealm: CameraRealm) {
        realm.write {
            val camera = query<CameraRealm>(query = "id == $0", cameraRealm.id).first().find()
            camera?.favorites = cameraRealm.favorites
        }
    }

    fun getCamerasData(): Flow<CamerasDataRealm?> {
        return realm.query<CamerasDataRealm>().asFlow().map { it.list.firstOrNull() }
    }
}