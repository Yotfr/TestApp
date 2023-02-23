package com.yotfr.testapp.ui.screens.cameras

import com.yotfr.testapp.data.datasource.local.model.cameras.CameraRealm
import com.yotfr.testapp.data.datasource.local.model.cameras.CamerasDataRealm
import com.yotfr.testapp.ui.screens.cameras.model.CameraUi
import com.yotfr.testapp.ui.screens.cameras.model.Room

fun CamerasDataRealm.toRooms(): List<Room> {
    val rooms = mutableListOf<Room>()
    this.room.forEach { roomName ->
        val camerasWithRoom = this.cameraRealms.filter {
            it.room == roomName
        }
        if (camerasWithRoom.isNotEmpty()) {
            rooms.add(
                Room(
                    name = roomName,
                    cameras = camerasWithRoom.map { it.toCameraUi() }
                )
            )
        }
    }
    val camerasWithNullRoom = this.cameraRealms.filter {
        it.room == null
    }
    rooms.add(
        Room(
            name = "Without room",
            cameras = camerasWithNullRoom.map { it.toCameraUi() }
        )
    )
    return rooms
}

fun CameraRealm.toCameraUi(): CameraUi {
    return CameraUi(
        id = this.id,
        name = this.name,
        rec = this.rec,
        room = this.room,
        snapshot = this.snapshot
    )
}