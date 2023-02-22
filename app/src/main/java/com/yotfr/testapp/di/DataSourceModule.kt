package com.yotfr.testapp.di

import com.yotfr.testapp.data.datasource.local.model.cameras.Camera
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasData
import com.yotfr.testapp.data.datasource.local.model.doors.Door
import com.yotfr.testapp.data.datasource.remote.CamerasApi
import com.yotfr.testapp.data.datasource.remote.DoorsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Camera::class,
                CamerasData::class,
                Door::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("http://cars.cprogroup.ru/")
            .build()
    }

    @Provides
    @Singleton
    fun provideCamerasApi(retrofit: Retrofit): CamerasApi {
        return retrofit.create(CamerasApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDoorsApi(retrofit: Retrofit): DoorsApi {
        return retrofit.create(DoorsApi::class.java)
    }
}
