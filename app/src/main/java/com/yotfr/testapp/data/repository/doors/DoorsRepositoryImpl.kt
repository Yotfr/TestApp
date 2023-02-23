package com.yotfr.testapp.data.repository.doors

import com.yotfr.testapp.data.datasource.local.dao.doors.DoorsDao
import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import com.yotfr.testapp.data.datasource.remote.DoorsApi
import com.yotfr.testapp.data.util.Cause
import com.yotfr.testapp.data.util.Response
import com.yotfr.testapp.data.util.toDoorsDataRealm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DoorsRepositoryImpl @Inject constructor(
    private val doorsDao: DoorsDao,
    private val doorsApi: DoorsApi
) : DoorsRepository {
    override fun getDoorsData(forceUpdate: Boolean): Flow<Response<DoorsDataRealm>> {
        return doorsDao.getDoorsData()
            .map {
                if (it == null || forceUpdate) {
                    try {
                        val fetchedDoorsData = doorsApi.fetchDoors()
                        doorsDao.deleteDoorsData()
                        doorsDao.insertDoorsData(fetchedDoorsData.toDoorsDataRealm())
                        Response.Success(
                            data = doorsDao.getDoorsData().first()
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Response.Exception(
                            cause = Cause.UnknownException
                        )
                    }
                } else {
                    Response.Success(
                        data = it
                    )
                }
            }
            .onStart {
                emit(Response.Loading())
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateDoor(doorRealm: DoorRealm) {
        doorsDao.updateDoor(
            doorRealm = doorRealm
        )
    }
}