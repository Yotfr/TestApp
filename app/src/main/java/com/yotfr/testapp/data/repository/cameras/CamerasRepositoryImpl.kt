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
import javax.inject.Inject

class CamerasRepositoryImpl @Inject constructor(
    private val camerasDao: CameraDao,
    private val camerasApi: CamerasApi
) : CamerasRepository {

    override fun getCamerasData(forceUpdate: Boolean): Flow<Response<CamerasDataRealm>> {
        return camerasDao.getCamerasData()
            .map {
                if (it == null || forceUpdate) {
                    try {
                        val fetchedCameraData = camerasApi.fetchCameras()
                        camerasDao.deleteCamerasData()
                        camerasDao.insertCamerasData(fetchedCameraData.toCamerasDataRealm())
                        Response.Success(
                            data = camerasDao.getCamerasData().first()
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

    override suspend fun updateCamera(cameraRealm: CameraRealm) {
        camerasDao.updateCamera(
            cameraRealm = cameraRealm
        )
    }
}
