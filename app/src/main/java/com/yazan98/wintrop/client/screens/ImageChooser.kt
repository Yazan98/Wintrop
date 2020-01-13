package com.yazan98.wintrop.client.screens

import androidx.annotation.DrawableRes
import com.yazan98.wintrop.client.R

object ImageChooser {

    @DrawableRes
    fun getImageByStatus(status: String): Int {
        return when (status) {
            "Mist" -> R.drawable.ic_broken_clouds
            "Light rain shower" -> R.drawable.ic_shower_rain
            "Clear" -> R.drawable.ic_clear_day
            "Partly cloudy" -> R.drawable.ic_cloudy_weather
            "Patchy rain possible" -> R.drawable.ic_snow_weather
            else -> R.drawable.ic_broken_clouds
        }
    }

}
