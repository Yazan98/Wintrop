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
): Parcelable {

    private constructor(builder: Builder) : this(
        tempFe = builder.tempFe,
        windDegree = builder.windDegree,
        windSpeedPerHours = builder.windSpeedPerHours,
        description = builder.description,
        hourly = builder.hourly,
        astronomy = builder.astronomy
    )


    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var tempFe: String = ""
        var windSpeedPerHours: String = ""
        var windDegree: String = ""
        var cityName: String = ""
        var description: List<WeatherDescription> = arrayListOf(WeatherDescription(value = cityName))
        var hourly: List<WeatherHour> = arrayListOf()
        var astronomy: List<WeatherTime> = arrayListOf()


        fun build() = ConditionResponse(
            tempFe = tempFe,
            windDegree = windDegree,
            windSpeedPerHours = windSpeedPerHours,
            description = description,
            hourly = hourly,
            astronomy = astronomy
        )

    }

}

@Parcelize
data class WeatherDescription(
    @SerializedName("value") var value: String = ""
): Parcelable
