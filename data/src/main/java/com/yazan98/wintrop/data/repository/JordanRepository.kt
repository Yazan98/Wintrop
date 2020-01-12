package com.yazan98.wintrop.data.repository

import com.yazan98.wintrop.data.WeatherRepository
import com.yazan98.wintrop.data.apis.JordanApi
import javax.inject.Inject

class JordanRepository @Inject constructor() : WeatherRepository<JordanApi>() {

    override suspend fun getService(): JordanApi {
        return serviceProvider.create(JordanApi::class.java)
    }

    suspend fun getAmmanStatus() {

    }

    suspend fun getIrbidStatus() {

    }

    suspend fun getAqabaStatus() {

    }

}
