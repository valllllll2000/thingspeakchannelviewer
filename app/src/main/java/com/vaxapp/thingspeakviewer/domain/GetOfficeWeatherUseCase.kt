package com.vaxapp.thingspeakviewer.domain

import io.reactivex.Single


class GetOfficeWeatherUseCase(val repository: OfficeWeatherRepository) {

    fun getOfficeWeather(): Single<DomainResponse> {
        return repository.getOfficeWeather()
    }

}