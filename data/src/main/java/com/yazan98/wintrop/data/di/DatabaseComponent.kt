package com.yazan98.wintrop.data.di

import com.yazan98.wintrop.data.database.dao.ConditionDao
import io.realm.Realm

@motif.Scope
interface DatabaseComponent {

    fun getRealmInstance(): Realm

    fun getConditionDao(): ConditionDao

    @motif.Objects
    open class DatabaseObjects {

        fun getRealmInstance(): Realm {
            return Realm.getDefaultInstance()
        }

        fun getConditionDao(database: Realm): ConditionDao {
            return ConditionDao(database)
        }

    }

}
