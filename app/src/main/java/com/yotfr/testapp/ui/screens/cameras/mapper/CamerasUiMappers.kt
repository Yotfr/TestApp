package com.yotfr.testapp.ui.screens.cameras.mapper

import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.ui.screens.cameras.model.CameraUi

fun CamerasDataRealm.toRooms(): Map<String?, List<CameraUi>> {
    return this.cameraRealms.groupBy { it.room }.mapValues {
        it.value.map { cameraRealm ->
            cameraRealm.toCameraUi()
        }
    }
}

fun CameraRealm.toCameraUi(): CameraUi {
    return CameraUi(
        id = id,
        name = name,
        rec = rec,
        room = room,
        snapshot = snapshot,
        isFavorite = favorites
    )
}

fun CameraUi.toCameraRealm(): CameraRealm {
    return CameraRealm().apply {
        id = this@toCameraRealm.id
        name = this@toCameraRealm.name
        room = this@toCameraRealm.room
        rec = this@toCameraRealm.rec
        favorites = this@toCameraRealm.isFavorite
        snapshot = this@toCameraRealm.snapshot
    }
}
