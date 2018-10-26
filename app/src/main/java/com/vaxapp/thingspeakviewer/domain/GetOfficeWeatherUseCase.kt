package com.vaxapp.thingspeakviewer.domain

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetOfficeWeatherUseCase(val repository: OfficeWeatherRepository) {

    fun getOfficeWeather(): Single<DomainResponse> {
        return repository.getOfficeWeather().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
    }
}