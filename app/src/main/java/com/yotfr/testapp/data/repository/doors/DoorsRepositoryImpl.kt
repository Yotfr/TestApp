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
                /*
                 The task says to fetch data only if there is no data in the local DB
                 or if the user refreshed the page (forceUpdate)
                 */
                if (it == null || forceUpdate) {
                    try {
                        // Try to fetch data, replace local cache and response with Response.Success
                        val fetchedDoorsData = doorsApi.fetchDoors()
                        doorsDao.deleteDoorsData()
                        doorsDao.insertDoorsData(fetchedDoorsData.toDoorsDataRealm())
                        Response.Success(
                            data = doorsDao.getDoorsData().first()
                        )
                    } catch (e: Exception) {
                        /*
                         Catch exceptions and response with Response.Exception with exception cause
                         In the future, checking exceptions logic can be added
                         (i.e. NoConnectionException)
                         */
                        e.printStackTrace()
                        Response.Exception(
                            cause = Cause.UnknownException
                        )
                    }
                } else {
                    // Response with Response.Success if data in the local DB exists
                    Response.Success(
                        data = it
                    )
                }
            }
            .onStart {
                // Emit Response.Loading before collecting
                emit(Response.Loading())
            }
            // Change thread
            .flowOn(Dispatchers.IO)
    }

    override suspend fun updateDoor(doorRealm: DoorRealm) {
        // Update camera (isFavorite field or name field)
        doorsDao.updateDoor(
            doorRealm = doorRealm
        )
    }
}
