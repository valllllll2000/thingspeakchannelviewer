package com.vaxapp.domain.interactor

import com.vaxapp.domain.entity.WeatherData
import com.vaxapp.domain.executor.PostExecutionThread
import com.vaxapp.domain.executor.ThreadExecutor
import com.vaxapp.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetWeatherData @Inject constructor(
        threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread,
        val weatherRepository: WeatherRepository) :
        SingleUseCase<WeatherData, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Single<WeatherData> {
        return weatherRepository.getWeather()
    }
}
