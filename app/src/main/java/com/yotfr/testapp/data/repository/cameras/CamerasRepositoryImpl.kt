package com.yotfr.testapp.data.repository.cameras

import com.yotfr.testapp.data.datasource.local.dao.cameras.CameraDao
import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.data.datasource.remote.CamerasApi
import com.yotfr.testapp.data.util.Cause
import com.yotfr.testapp.data.util.Response
import com.yotfr.testapp.data.util.toCamerasDataRealm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CamerasRepositoryImpl @Inject constructor(
    private val camerasDao: CameraDao,
    private val camerasApi: CamerasApi
) : CamerasRepository {

    override fun getCamerasData(forceUpdate: Boolean): Flow<Response<CamerasDataRealm>> {
        return camerasDao.getCamerasData()
            .map {
                /*
                 The task says to fetch data only if there is no data in the local DB
                 or if the user refreshed the page (forceUpdate)
                 */
                if (it == null || forceUpdate) {
                    try {
                        // Try to fetch data, replace local cache and response with Response.Success
                        val fetchedCameraData = camerasApi.fetchCameras()
                        camerasDao.deleteCamerasData()
                        camerasDao.insertCamerasData(fetchedCameraData.toCamerasDataRealm())
                        Response.Success(
                            data = camerasDao.getCamerasData().first()
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

    override suspend fun updateCamera(cameraRealm: CameraRealm) {
        // Update camera (isFavorite field)
        withContext(Dispatchers.IO) {
            camerasDao.updateCamera(
                cameraRealm = cameraRealm
            )
        }
    }
}
