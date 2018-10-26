package com.vaxapp.thingspeakviewer.view.main

import com.vaxapp.thingspeakviewer.domain.DomainResponse
import com.vaxapp.thingspeakviewer.domain.GetOfficeWeatherUseCase
import io.reactivex.disposables.Disposable


class MainPresenter(private val useCase: GetOfficeWeatherUseCase, private val mapper: ViewResponseMapper) {

    var view: MainView? = null
    private var disposable: Disposable? = null

    fun onViewReady() {
        view?.showLoading()
        disposable = useCase.getOfficeWeather()
                .subscribe({ result -> display(result) },
                        { error -> view?.showError(error) })
    }

    private fun display(result: DomainResponse) {
        val response = mapper.toViewResponse(result)
        view?.hideLoading()
        view?.display(response)
    }

    fun dispose() {
        disposable?.dispose()
    }
}