package com.yotfr.testapp.data.datasource.local.model.doors

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DoorRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var favorites: Boolean = false
    var name: String = ""
    var room: String = ""
    var snapshot: String = ""
}