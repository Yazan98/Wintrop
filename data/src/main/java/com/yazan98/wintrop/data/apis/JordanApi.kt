package com.yazan98.wintrop.data.apis

import com.yazan98.wintrop.data.BuildConfig
import com.yazan98.wintrop.data.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JordanApi {

    @GET("weather.ashx")
    fun getWeatherStatusByCityName(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json",
        @Query("query") query: String
    ): Observable<WeatherResponse>

}
