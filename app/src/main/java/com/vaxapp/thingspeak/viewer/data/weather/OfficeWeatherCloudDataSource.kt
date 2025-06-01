package com.vaxapp.thingspeak.viewer.data.weather

import com.vaxapp.thingspeak.viewer.data.ApiService
import io.reactivex.Single

class OfficeWeatherCloudDataSource(private val service: ApiService) {
    fun fetchWeather(): Single<WeatherResponse> {
        return service.fetchFields()
    }
}
