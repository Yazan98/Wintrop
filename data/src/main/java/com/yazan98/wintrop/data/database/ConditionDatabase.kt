package com.yazan98.wintrop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yazan98.wintrop.data.database.daos.ConditionDao
import com.yazan98.wintrop.data.database.daos.ConditionMonthDao
import com.yazan98.wintrop.data.database.daos.DaysDao
import com.yazan98.wintrop.data.database.models.ConditionDay
import com.yazan98.wintrop.data.database.models.ConditionEntity
import com.yazan98.wintrop.data.database.models.ConditionMonth

@Database(
    entities = [ConditionEntity::class, ConditionDay::class, ConditionMonth::class],
    version = 2
)
abstract class ConditionDatabase : RoomDatabase() {

    abstract fun conditionsDao(): ConditionDao
    abstract fun daysDao(): DaysDao
    abstract fun monthsDao(): ConditionMonthDao

}
