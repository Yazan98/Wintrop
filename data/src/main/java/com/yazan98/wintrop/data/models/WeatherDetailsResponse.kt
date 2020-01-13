package com.yazan98.wintrop.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDetailsResponse(
    val request: List<WeatherRequest>,
    val weather: List<Weather>,
    @SerializedName("current_condition") val currentConditions: List<ConditionResponse>
) : Parcelable

@Parcelize
data class WeatherRequest(
    var type: String = "",
    var query: String = ""
) : Parcelable

@Parcelize
data class WeatherTime(
    var sunrise: String = "",
    var moon_phase: String = ""
) : Parcelable

@Parcelize
data class WeatherHour(
    var tempF: String = "",
    @SerializedName("windspeedMiles") var windSpeed: String = "",
    val weatherDesc: List<WeatherDescription>
) : Parcelable

