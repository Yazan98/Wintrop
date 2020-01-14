package com.yazan98.wintrop.data.mappers

import com.yazan98.wintrop.data.VortexOneWayMapper
import com.yazan98.wintrop.data.database.models.ConditionMonth
import com.yazan98.wintrop.data.models.MonthResponse

class MonthMapper: VortexOneWayMapper<ConditionMonth, MonthResponse> {
    override fun from(from: ConditionMonth): MonthResponse {
        return MonthResponse(
            name = from.name,
            avgRain = "",
            mavTemp = from.up,
            minTemp = from.down
        )
    }

    override fun to(to: MonthResponse): ConditionMonth {
        return ConditionMonth(
            cityName = to.cityName,
            up = to.mavTemp,
            down = to.minTemp,
            name = to.name
        )
    }
}