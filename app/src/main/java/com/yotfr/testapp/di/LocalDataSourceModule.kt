package com.yotfr.testapp.di

import com.yotfr.testapp.data.datasource.local.dao.cameras.CameraDao
import com.yotfr.testapp.data.datasource.local.dao.doors.DoorsDao
import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CameraRealm::class,
                CamerasDataRealm::class,
                DoorRealm::class,
                DoorsDataRealm::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Provides
    @Singleton
    fun provideCamerasDao(realm: Realm): CameraDao {
        return CameraDao(
            realm = realm
        )
    }

    @Provides
    @Singleton
    fun provideDoorsDao(realm: Realm): DoorsDao {
        return DoorsDao(
            realm = realm
        )
    }
}