package com.vaxapp.domain.repository

import com.vaxapp.domain.entity.WeatherData
import io.reactivex.Single


interface WeatherRepository {

    fun getWeather(): Single<WeatherData>
}
