package com.yazan98.wintrop.data.database

import androidx.lifecycle.LiveData
import io.realm.RealmObject
import io.realm.RealmResults

interface DatabaseOperations<T: RealmObject> {

    suspend fun insertCondition(entity: T)

    suspend fun getConditionByCityName(cityName: String): LiveData<RealmResults<T>>

    suspend fun deleteConditionById(id: String)

    suspend fun clearConditions()

    suspend fun getConditionsNumber(): Int

}
