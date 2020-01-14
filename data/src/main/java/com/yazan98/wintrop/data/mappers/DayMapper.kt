package com.yazan98.wintrop.data.mappers

import com.yazan98.wintrop.data.VortexOneWayMapper
import com.yazan98.wintrop.data.database.models.ConditionDay
import com.yazan98.wintrop.data.models.HourlyResponse
import com.yazan98.wintrop.data.models.Weather
import com.yazan98.wintrop.data.models.WeatherDescription

class DayMapper : VortexOneWayMapper<ConditionDay, Weather> {
    override fun from(from: ConditionDay): Weather {
        return Weather(
            date = from.date,
            hours = arrayListOf(HourlyResponse(from.tempF, "" , arrayListOf(WeatherDescription(from.status)))),
            maxF = from.up,
            minF = from.down,
            times = arrayListOf()
        )
    }

    /**
     * Im Always Using Index 0 Because This Is just A Demo Application
     */
    override fun to(to: Weather): ConditionDay {
        return ConditionDay(
            cityName = to.cityName,
            tempF = to.hours[0].tempF,
            status = to.hours[0].description[0].value,
            down = to.minF,
            up = to.maxF
        )
    }
}
