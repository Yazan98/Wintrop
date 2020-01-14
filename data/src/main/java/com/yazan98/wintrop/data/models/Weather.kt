package com.yazan98.wintrop.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    var date: String = "",
    @SerializedName("astronomy") var times: List<AstronomyResponse>,
    @SerializedName("maxtempF") var maxF: String = "",
    @SerializedName("mintempF") var minF: String = "",
    @SerializedName("hourly") var hours: List<HourlyResponse>,
    var cityName: String = ""
): Parcelable

@Parcelize
data class AstronomyResponse(
    var sunrise: String = "",
   @SerializedName("moon_phase") var shapeValue: String = ""
): Parcelable

@Parcelize
data class HourlyResponse(
    @SerializedName("tempF") var tempF: String = "",
    @SerializedName("windspeedKmph") var windSpeed: String = "",
    @SerializedName("weatherDesc") val description: List<WeatherDescription>
): Parcelable
