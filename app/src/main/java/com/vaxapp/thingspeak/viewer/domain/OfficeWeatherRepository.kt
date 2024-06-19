package com.vaxapp.thingspeak.viewer.domain

import io.reactivex.Single

interface OfficeWeatherRepository {

    fun getOfficeWeather(): Single<DomainResponse>
}
