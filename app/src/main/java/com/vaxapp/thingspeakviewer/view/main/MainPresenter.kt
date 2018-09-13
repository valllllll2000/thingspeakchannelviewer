package com.vaxapp.thingspeakviewer.view.main

import com.vaxapp.thingspeakviewer.domain.GetOfficeWeatherUseCase


class MainPresenter(private val useCase: GetOfficeWeatherUseCase) {

    fun onViewReady() {
        useCase.getOfficeWeather().map { it ->  }
    }
}