package com.yazan98.wintrop.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yazan98.wintrop.data.database.DaoExecutor
import com.yazan98.wintrop.data.database.models.ConditionMonth

@Dao
interface ConditionMonthDao : DaoExecutor<ConditionMonth> {

    @Insert
    override fun insertEntity(entity: ConditionMonth)

    @Query("SELECT * FROM ConditionMonth")
    override fun getAll():List<ConditionMonth>

    @Query("SELECT * FROM ConditionMonth WHERE id = :id")
    override fun getEntityById(id: String): ConditionMonth

    @Query("DELETE FROM ConditionMonth WHERE id = :id")
    override fun deleteEntityById(id: String)

    @Query("SELECT * FROM ConditionMonth WHERE city_name = :cityName")
    fun getByCityName(cityName: String): List<ConditionMonth>
}