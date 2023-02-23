package com.yotfr.testapp.di

import com.yotfr.testapp.data.repository.cameras.CamerasRepository
import com.yotfr.testapp.data.repository.cameras.CamerasRepositoryImpl
import com.yotfr.testapp.data.repository.doors.DoorsRepository
import com.yotfr.testapp.data.repository.doors.DoorsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    @Singleton
    abstract fun bindCamerasRepository_to_CameraRepositoryImpl(
        camerasRepositoryImpl: CamerasRepositoryImpl
    ): CamerasRepository

    @Binds
    @Singleton
    abstract fun bindDoorsRepository_to_DoorsRepositoryImpl(
        doorsRepositoryImpl: DoorsRepositoryImpl
    ): DoorsRepository
}
