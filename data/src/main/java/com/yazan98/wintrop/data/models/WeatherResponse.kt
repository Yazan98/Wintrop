package com.yazan98.wintrop.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    val data: WeatherDetailsResponse
): Parcelable
