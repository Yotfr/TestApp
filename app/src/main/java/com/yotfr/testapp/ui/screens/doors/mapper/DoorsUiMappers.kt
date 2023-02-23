package com.yotfr.testapp.ui.screens.doors.mapper

import com.yotfr.testapp.data.datasource.local.model.doors.DoorRealm
import com.yotfr.testapp.ui.screens.doors.model.DoorUi

fun DoorRealm.toDoorUi(): DoorUi {
    return DoorUi(
        id = this.id,
        favorites = this.favorites,
        name = this.name,
        room = this.room,
        snapshot = this.snapshot
    )
}