package com.yotfr.testapp.data.datasource.local.dao.doors

import com.yotfr.testapp.data.datasource.local.model.doors.DoorsData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DoorsDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertDoorsData(doorsData: DoorsData) {
        realm.write { copyToRealm(doorsData) }
    }
    suspend fun deleteDoorsData(doorsData: DoorsData) {
        realm.write {
            val camerasDataQuery = query<DoorsData>().find()
            delete(camerasDataQuery)
        }
    }
    fun getCamerasData(): Flow<List<DoorsData>> {
        return realm.query<DoorsData>().asFlow().map { it.list }
    }
}