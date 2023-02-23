package com.yotfr.testapp.data.repository.cameras

import android.util.Log
import com.yotfr.testapp.data.datasource.local.dao.cameras.CameraDao
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

    override fun getCamerasData(): Flow<Response<CamerasDataRealm>> = flow {
        emit(Response.Loading())
        val camerasDataCache = camerasDao.getCamerasData().firstOrNull()
        if (camerasDataCache == null) {
            try {
                val fetchedCameraData = camerasApi.fetchCameras()
                camerasDao.deleteCamerasData()
                camerasDao.insertCamerasData(fetchedCameraData.toCamerasDataRealm())
                val updatedCamerasDataCache = camerasDao.getCamerasData().first()
                emit(
                    Response.Success(
                        data = updatedCamerasDataCache
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Response.Exception(
                        cause = Cause.UnknownException
                    )
                )
            }
        } else {
            emit(
                Response.Success(
                    data = camerasDataCache
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}
