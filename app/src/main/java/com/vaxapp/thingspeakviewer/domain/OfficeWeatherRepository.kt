package com.vaxapp.thingspeakviewer.domain

import io.reactivex.Single

interface OfficeWeatherRepository {

    fun getOfficeWeather(): Single<DomainResponse>
}
