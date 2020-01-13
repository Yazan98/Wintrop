package com.yazan98.wintrop.data.database.dao

import androidx.lifecycle.LiveData
import com.yazan98.wintrop.data.database.DatabaseOperations
import com.yazan98.wintrop.data.database.asLiveData
import com.yazan98.wintrop.data.database.models.ConditionEntity
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ConditionDao(private val database: Realm) : DatabaseOperations<ConditionEntity> {

    override suspend fun insertCondition(entity: ConditionEntity) {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                it.insert(entity)
            }
        }
    }

    override suspend fun getConditionByCityName(cityName: String): LiveData<RealmResults<ConditionEntity>> {
        return withContext(Dispatchers.IO) {
            database.where(ConditionEntity::class.java).equalTo("city_name", cityName)
                .findAllAsync().asLiveData()
        }
    }

    override suspend fun deleteConditionById(id: String) {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                val result = it.where(ConditionEntity::class.java).equalTo("id", id).findFirst()
                result?.deleteFromRealm()
            }
        }
    }

    override suspend fun clearConditions() {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                val result = it.where(ConditionEntity::class.java).findAll()
                result.deleteAllFromRealm()
            }
        }
    }

    override suspend fun getConditionsNumber() = suspendCoroutine<Int> { cont ->
        try {
            database.executeTransactionAsync {
                val result = it.where(ConditionEntity::class.java).findAll()
                cont.resume( result.size)
            }
        } catch (ex: Exception) {
            cont.resumeWithException(ex)
        }
    }

}
