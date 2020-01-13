package com.yazan98.wintrop.data.database.models

import com.yazan98.wintrop.data.models.ConditionResponse
import com.yazan98.wintrop.data.models.WeatherDescription
import com.yazan98.wintrop.data.models.WeatherHour
import com.yazan98.wintrop.data.models.WeatherTime
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.*
import java.util.*
import javax.inject.Inject

@RealmClass(name = "Condition", fieldNamingPolicy = RealmNamingPolicy.PASCAL_CASE)
open class ConditionEntity @Inject constructor() : RealmObject(), RealmModel {
    @PrimaryKey @RealmField(name = "id") var id: String = UUID.randomUUID().toString()
    @Required @RealmField(name = "city_name") var cityName: String = ""
    @Required @RealmField(name = "temp_f") var tempF: String = ""
    @Required @RealmField(name = "status") var status: String = ""
    @Required @RealmField(name = "wind_speed_per_k") var windSpeedPreK: String = ""
    @Required @RealmField(name = "wind_degree") var windDegree: String = ""
    @RealmField(name = "days") var days: RealmList<DaysStatus> = RealmList<DaysStatus>()

//    private constructor(builder: Builder) : this() {
//        tempF = builder.tempFe
//        windDegree = builder.windDegree
//        windSpeedPreK = builder.windSpeedPerHours
//        cityName = builder.cityName
//    }
//
//    companion object {
//        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
//    }
//
//    class Builder {
//        var tempFe: String = ""
//        var windSpeedPerHours: String = ""
//        var windDegree: String = ""
//        var cityName: String = ""
//        var days: List<>
//
//        fun build() = ConditionEntity(
//            tempFe = tempFe,
//            windDegree = windDegree,
//            windSpeedPerHours = windSpeedPerHours
//        )
//
//    }

}

open class DaysStatus @Inject constructor() : RealmObject(), RealmModel {
    @Required @RealmField(name = "date") var date: String = ""
    @Required @RealmField(name = "temp_f") var tempF: String = ""
    @Required @RealmField(name = "up") var up: String = ""
    @Required @RealmField(name = "down") var down: String = ""
}
