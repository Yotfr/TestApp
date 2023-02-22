package com.yotfr.testapp.data.datasource.local.model.cameras

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Camera : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var rec: Boolean = false
    var room: String = ""
    var snapshot: String = ""
}