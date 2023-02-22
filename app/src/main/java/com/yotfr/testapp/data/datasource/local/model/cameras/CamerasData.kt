package com.yotfr.testapp.data.datasource.local.model.cameras

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CamerasData : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var cameras: RealmList<Camera> = realmListOf()
    var room: RealmList<String> = realmListOf()
}