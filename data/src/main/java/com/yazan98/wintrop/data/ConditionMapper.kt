package com.yazan98.wintrop.data

import com.yazan98.wintrop.data.database.models.ConditionEntity
import com.yazan98.wintrop.data.models.ConditionResponse
import com.yazan98.wintrop.data.models.WeatherDescription

class ConditionMapper : VortexOneWayMapper<ConditionEntity, ConditionResponse> {

    override fun from(from: ConditionEntity): ConditionResponse {
        return ConditionResponse.build {
            cityName = from.cityName
            description = arrayListOf(WeatherDescription(from.status))
            tempFe = from.tempF
            windDegree = from.windDegree
            windSpeedPerHours = from.windSpeedPreK
        }
    }

    override fun to(to: ConditionResponse): ConditionEntity {
        return ConditionEntity()
    }

}
