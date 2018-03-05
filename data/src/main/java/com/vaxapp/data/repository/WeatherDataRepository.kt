package com.vaxapp.data.repository

import com.vaxapp.domain.entity.WeatherData
import com.vaxapp.domain.repository.WeatherRepository
import io.reactivex.Single


open class WeatherDataRepository constructor(): WeatherRepository {
    override fun getWeather(): Single<WeatherData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}