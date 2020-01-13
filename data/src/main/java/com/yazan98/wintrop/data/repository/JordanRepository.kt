package com.yazan98.wintrop.data.repository

import com.yazan98.wintrop.data.WeatherRepository
import com.yazan98.wintrop.data.apis.JordanApi
import com.yazan98.wintrop.data.models.WeatherResponse
import io.reactivex.Observable
import javax.inject.Inject

class JordanRepository @Inject constructor() : WeatherRepository<JordanApi>() {

    override suspend fun getService(): JordanApi {
        return serviceProvider.create(JordanApi::class.java)
    }

    suspend fun getAmmanStatus(): Observable<WeatherResponse> {
        return getService().getWeatherStatusByCityName(query = "Amman")
    }

    suspend fun getIrbidStatus(): Observable<WeatherResponse> {
        return getService().getWeatherStatusByCityName(query = "Irbid")
    }

    suspend fun getAqabaStatus(): Observable<WeatherResponse> {
        return getService().getWeatherStatusByCityName(query = "Aqaba")
    }

}
