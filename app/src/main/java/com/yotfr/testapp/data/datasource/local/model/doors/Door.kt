package com.yotfr.testapp.data.datasource.local.model.doors

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Door : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var favorites: Boolean = false
    var name: String = ""
    var room: String = ""
    var snapshot: String = ""
}