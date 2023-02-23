package com.yotfr.testapp.data.repository.doors

import com.yotfr.testapp.data.datasource.local.dao.doors.DoorsDao
import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import com.yotfr.testapp.data.datasource.remote.DoorsApi
import com.yotfr.testapp.data.util.Cause
import com.yotfr.testapp.data.util.Response
import com.yotfr.testapp.data.util.toDoorsDataRealm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoorsRepositoryImpl @Inject constructor(
    private val doorsDao: DoorsDao,
    private val doorsApi: DoorsApi
) : DoorsRepository {
    override fun getDoorsData(): Flow<Response<DoorsDataRealm>> = flow {
        val doorsDataCache = doorsDao.getDoorsData().firstOrNull()
        if (doorsDataCache == null) {
            try {
                emit(Response.Loading())
                val fetchedDoorsData = doorsApi.fetchDoors()
                doorsDao.deleteDoorsData()
                doorsDao.insertDoorsData(
                    doorsDataRealm = fetchedDoorsData.toDoorsDataRealm()
                )
                val updatedDoorsDataCache = doorsDao.getDoorsData().first()
                emit(
                    Response.Success(
                        data = updatedDoorsDataCache
                    )
                )
            } catch (e: Exception) {
                throw e
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
                    data = doorsDataCache
                )
            )
        }
    }
}