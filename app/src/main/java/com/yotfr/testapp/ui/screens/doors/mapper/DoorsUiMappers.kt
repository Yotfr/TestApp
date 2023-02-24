package com.yotfr.testapp.ui.screens.doors.mapper

import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.ui.screens.doors.model.DoorUi

fun DoorRealm.toDoorUi(): DoorUi {
    return DoorUi(
        id = this.id,
        favorites = this.favorites,
        name = this.name,
        room = this.room,
        snapshot = this.snapshot,
        isOpen = this.isOpen
    )
}

fun DoorUi.toDoorRealm(): DoorRealm {
    return DoorRealm().apply {
        id = this@toDoorRealm.id
        favorites = this@toDoorRealm.favorites
        name = this@toDoorRealm.name
        room = this@toDoorRealm.room
        snapshot = this@toDoorRealm.snapshot
        isOpen = this@toDoorRealm.isOpen
    }
}
