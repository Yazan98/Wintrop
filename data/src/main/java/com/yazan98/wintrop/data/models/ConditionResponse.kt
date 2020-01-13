package com.yazan98.wintrop.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConditionResponse(
    @SerializedName("observation_time") var observationTime: String = "",
    @SerializedName("temp_C") var tempC: String = "",
    @SerializedName("temp_F") var tempFe: String = "",
    @SerializedName("windspeedKmph") var windSpeedPerHours: String = "",
    @SerializedName("winddirDegree") var windDegree: String = "",
    @SerializedName("maxtempF") var maxF: String = "",
    @SerializedName("mintempF") var minF: String = "",
    @SerializedName("weatherDesc") var description: List<WeatherDescription>,
    val astronomy: List<WeatherTime>,
    val hourly: List<WeatherHour>,
    var weatherCode: String = ""
): Parcelable

@Parcelize
data class WeatherDescription(
    @SerializedName("value") var value: String = ""
): Parcelable
