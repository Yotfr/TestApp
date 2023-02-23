package com.yotfr.testapp.data.util

import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.data.datasource.local.model.doors.DoorsDataRealm
import com.yotfr.testapp.data.datasource.remote.dto.cameras.CameraDto
import com.yotfr.testapp.data.datasource.remote.dto.cameras.CamerasDto
import com.yotfr.testapp.data.datasource.remote.dto.doors.DoorsDataDto
import com.yotfr.testapp.data.datasource.remote.dto.doors.DoorsDto
import io.realm.kotlin.ext.toRealmList

fun CamerasDto.toCamerasDataRealm(): CamerasDataRealm {
    return CamerasDataRealm().apply {
        cameraRealms = this@toCamerasDataRealm.camerasDataDTO.cameraDtos.map {
            it.toCameraRealm()
        }.toRealmList()
        room = this@toCamerasDataRealm.camerasDataDTO.room.toRealmList()
    }
}

fun CameraDto.toCameraRealm(): CameraRealm {
    return CameraRealm().apply {
        id = this@toCameraRealm.id
        name = this@toCameraRealm.name
        rec = this@toCameraRealm.rec
        room = this@toCameraRealm.room
        snapshot = this@toCameraRealm.snapshot
    }
}

fun DoorsDto.toDoorsDataRealm(): DoorsDataRealm {
    return DoorsDataRealm().apply {
        doorRealms = this@toDoorsDataRealm.data.map {
            it.toDoorRealm()
        }.toRealmList()
    }
}

fun DoorsDataDto.toDoorRealm(): DoorRealm {
    return DoorRealm().apply {
        id = this@toDoorRealm.id
        favorites = this@toDoorRealm.favorites
        name = this@toDoorRealm.name
        room = this@toDoorRealm.room
        snapshot = this@toDoorRealm.snapshot
    }
}
