package com.vaxapp.thingspeak.viewer.domain

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetOfficeWeatherUseCase(private val repository: OfficeWeatherRepository) {

    fun getOfficeWeather(): Single<DomainResponse> {
        return repository.getOfficeWeather().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
    }
}
