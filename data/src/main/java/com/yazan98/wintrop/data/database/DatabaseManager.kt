package com.yazan98.wintrop.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.yazan98.wintrop.data.database.daos.ConditionDao
import com.yazan98.wintrop.data.database.daos.ConditionMonthDao
import com.yazan98.wintrop.data.database.daos.DaysDao
import com.yazan98.wintrop.data.database.models.ConditionEntity
import com.yazan98.wintrop.data.mappers.ConditionMapper
import com.yazan98.wintrop.data.mappers.DayMapper
import com.yazan98.wintrop.data.mappers.MonthMapper
import com.yazan98.wintrop.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseManager {

    lateinit var database: ConditionDatabase
    private val mapper: ConditionMapper by lazy {
        ConditionMapper()
    }

    private  val dayMapper: DayMapper by lazy {
        DayMapper()
    }

    private val monthMapper: MonthMapper by lazy {
        MonthMapper()
    }

    suspend fun initDatabase(context: Context) {
        withContext(Dispatchers.IO) {
            database = Room.databaseBuilder(
                context,
                ConditionDatabase::class.java, "WintropDatabase"
            ).build()
        }
    }

    suspend fun insertIntoDatabase(response: WeatherResponse) {
        withContext(Dispatchers.IO) {
            getConditionDao().insertEntity(mapper.to(response.data.currentConditions[0]))
            dayMapper to response.data.weather.forEach {
                getDaysDao().insertEntity(dayMapper.to(it))
            }

            response.data.avarage[0].month.forEach {
                getMonthsDao().insertEntity(monthMapper.to(it))
            }
        }
    }

    suspend fun getAllConditions(cityName: String): List<ConditionEntity> {
        return withContext(Dispatchers.IO) {
            getConditionDao().getEntityByCityName(cityName)
        }
    }

    suspend fun getResponseByCityName(cityName: String) : WeatherResponse {
        return withContext(Dispatchers.IO) {
            val days = ArrayList<Weather>()
            val months = ArrayList<MonthResponse>()
            val condition = getConditionDao().getEntityByCityName(cityName)
            getDaysDao().getDaysByCityName(cityName).forEach {
                days.add(dayMapper.from(it))
            }

            getMonthsDao().getByCityName(cityName).forEach {
                months.add(monthMapper.from(it))
            }

            WeatherResponse(
                WeatherDetailsResponse(
                    request = arrayListOf(condition[0].cityName?.let { WeatherRequest("City" , it) }) as List<WeatherRequest>,
                    avarage = arrayListOf(AvarageResponse(months)),
                    weather = days,
                    currentConditions = listOf(condition?.let { mapper.from(it[0]) }) as List<ConditionResponse>
                )
            )
        }
    }

    private suspend fun getConditionDao(): ConditionDao {
        return withContext(Dispatchers.IO) {
            database.conditionsDao()
        }
    }

    private suspend fun getMonthsDao(): ConditionMonthDao {
        return withContext(Dispatchers.IO) {
            database.monthsDao()
        }
    }

    private suspend fun getDaysDao(): DaysDao {
        return withContext(Dispatchers.IO) {
            database.daysDao()
        }
    }


}