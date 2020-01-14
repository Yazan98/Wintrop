package com.yazan98.wintrop.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yazan98.wintrop.data.database.DaoExecutor
import com.yazan98.wintrop.data.database.models.ConditionEntity

@Dao
interface ConditionDao : DaoExecutor<ConditionEntity> {

    @Insert
    override fun insertEntity(entity: ConditionEntity)

    @Query("SELECT * FROM ConditionEntity")
    override fun getAll(): List<ConditionEntity>

    @Query("SELECT * FROM ConditionEntity WHERE id = :id")
    override fun getEntityById(id: String): ConditionEntity

    @Query("DELETE FROM ConditionEntity WHERE id = :id")
    override fun deleteEntityById(id: String)

    @Query("SELECT * FROM ConditionEntity WHERE city_name = :cityName")
    fun getEntityByCityName(cityName: String): List<ConditionEntity>

}
