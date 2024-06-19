package com.vaxapp.thingspeak.viewer.view.main

import com.vaxapp.thingspeak.viewer.domain.DomainResponse
import com.vaxapp.thingspeak.viewer.domain.GetNotificationPreferenceUseCase
import com.vaxapp.thingspeak.viewer.domain.GetOfficeWeatherUseCase
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.coroutines.CoroutineContext

class MainPresenter(
    private val useCase: GetOfficeWeatherUseCase,
    private val mapper: ViewResponseMapper,
    private val getNotificationPreferenceUseCase: GetNotificationPreferenceUseCase,
    private val coroutineContext: CoroutineContext
) {

    var view: MainView? = null
    private var disposable: Disposable? = null

    fun onViewReady() {
        view?.showLoading()
        disposable = useCase.getOfficeWeather()
            .subscribe({ result -> useResult(result) },
                { error -> view?.showError(error) })
    }

    private fun useResult(result: DomainResponse) {
        val channelUpdateTime = result.feeds.first().createdAt?.time ?: 0L
        if (Date().time - channelUpdateTime > 360000L) {
            CoroutineScope(coroutineContext).launch {
                val displayNotification =
                    getNotificationPreferenceUseCase.getNotificationPreference()
                if (displayNotification) {
                    view?.displayNotification()
                }
            }
        }
        display(result)
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
