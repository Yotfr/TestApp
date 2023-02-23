package com.yotfr.testapp.data.datasource.local.model.cameras

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CameraRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var rec: Boolean = false
    var room: String? = null
    var snapshot: String = ""
}