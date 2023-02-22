package com.yotfr.testapp.data.datasource.local.model.doors

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DoorsData : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var doors: RealmList<Door> = realmListOf()
}
