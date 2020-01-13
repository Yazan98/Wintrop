package com.yazan98.wintrop.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonthResponse(
    var index: Long = 0,
    var name: String = "",
    @SerializedName("avgMinTemp_F") var minTemp: String = "",
    @SerializedName("absMaxTemp_F") var mavTemp: String = "",
    @SerializedName("avgDailyRainfall") var avgRain: String = ""
): Parcelable
