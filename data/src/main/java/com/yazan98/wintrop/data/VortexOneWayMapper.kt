package com.yazan98.wintrop.data

interface VortexOneWayMapper<From, To> {

    fun from(from: From): To

    fun to(to: To): From

}
