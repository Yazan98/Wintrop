package com.yazan98.wintrop.data.mappers

import com.yazan98.wintrop.data.VortexOneWayMapper
import com.yazan98.wintrop.data.database.models.ConditionEntity
import com.yazan98.wintrop.data.models.ConditionResponse
import com.yazan98.wintrop.data.models.WeatherDescription

class ConditionMapper :
    VortexOneWayMapper<ConditionEntity, ConditionResponse> {

    override fun from(from: ConditionEntity): ConditionResponse {
        return ConditionResponse.build {
            cityName = from.cityName
            description = arrayListOf(WeatherDescription(from.status))
            tempFe = from.tempF
            windDegree = from.windDegree
            windSpeedPerHours = from.windSpeedPreK
        }
    }

    /**
     * Replace This with Builder Like From Function .... but this is for now at Demo Application
     */
    override fun to(to: ConditionResponse): ConditionEntity {
        return ConditionEntity(
            cityName = to.cityName,
            windDegree = to.windDegree,
            status = to.description[0].value,
            tempF = to.tempFe,
            windSpeedPreK = to.windSpeedPerHours
        )
    }

}
