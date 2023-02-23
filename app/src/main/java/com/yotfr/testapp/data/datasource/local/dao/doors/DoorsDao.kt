package com.yotfr.testapp.data.datasource.local.dao.doors

import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DoorsDao(
    private val realm: Realm
) {
    suspend fun insertDoorsData(doorsDataRealm: DoorsDataRealm) {
        realm.write { copyToRealm(doorsDataRealm) }
    }
    suspend fun deleteDoorsData() {
        realm.write {
            val camerasDataQuery = query<DoorsDataRealm>().find()
            delete(camerasDataQuery)
        }
    }
    fun getDoorsData(): Flow<DoorsDataRealm> {
        return realm.query<DoorsDataRealm>().asFlow().map { it.list.first() }
    }
}
