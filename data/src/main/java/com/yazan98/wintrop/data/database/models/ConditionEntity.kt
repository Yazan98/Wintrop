package com.yazan98.wintrop.data.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Here i want to save List<Days> but Room Database doesn't allow object references
 * https://developer.android.com/training/data-storage/room/referencing-data.html#understand-no-object-references
 */
@Entity
@Parcelize
data class ConditionEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "city_name") var cityName: String = "",
    @ColumnInfo(name = "temp_f") var tempF: String = "",
    @ColumnInfo(name = "status") var status: String = "",
    @ColumnInfo(name = "wind_speed") var windSpeedPreK: String = "",
    @ColumnInfo(name = "wind_degree") var windDegree: String = ""
): Parcelable

@Entity
@Parcelize
data class ConditionDay(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "city_name") var cityName: String = "",
    @ColumnInfo(name = "temp_f") var tempF: String = "",
    @ColumnInfo(name = "status") var status: String = "",
    @ColumnInfo(name = "up") var up: String = "",
    @ColumnInfo(name = "down") var down: String = "",
    @ColumnInfo(name = "date") var date: String = ""
): Parcelable

@Entity
@Parcelize
data class ConditionMonth(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "city_name") var cityName: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "up") var up: String = "",
    @ColumnInfo(name = "down") var down: String = ""
): Parcelable
