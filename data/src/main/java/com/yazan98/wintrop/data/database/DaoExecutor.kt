package com.yazan98.wintrop.data.database

interface DaoExecutor<T> {

    fun insertEntity(entity: T)

    fun getEntityById(id: String): T

    fun getAll(): List<T>

    fun deleteEntityById(id: String)

}
