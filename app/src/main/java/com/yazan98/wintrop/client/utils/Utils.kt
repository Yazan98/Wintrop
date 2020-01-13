package com.yazan98.wintrop.client.utils

import androidx.annotation.DrawableRes
import com.yazan98.wintrop.client.R

object Utils {

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

    val colors = arrayListOf<Int>(
        R.drawable.bg_item_1,
        R.drawable.bg_item_2,
        R.drawable.bg_item_3,
        R.drawable.bg_item_4,
        R.drawable.bg_item_5,
        R.drawable.bg_item_6,
        R.drawable.bg_item_7,
        R.drawable.bg_item_8
    )

}
