package com.yazan98.wintrop.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yazan98.wintrop.data.database.DaoExecutor
import com.yazan98.wintrop.data.database.models.ConditionDay

@Dao
interface DaysDao : DaoExecutor<ConditionDay> {

    @Query("SELECT * FROM ConditionDay")
    override fun getAll(): List<ConditionDay>

    @Query("SELECT * FROM ConditionDay WHERE id = :id")
    override fun getEntityById(id: String): ConditionDay

    @Insert
    override fun insertEntity(entity: ConditionDay)

    @Query("DELETE FROM ConditionDay WHERE id = :id")
    override fun deleteEntityById(id: String)

    @Query("SELECT * FROM ConditionDay WHERE city_name = :cityName")
    fun getDaysByCityName(cityName: String): List<ConditionDay>

}
