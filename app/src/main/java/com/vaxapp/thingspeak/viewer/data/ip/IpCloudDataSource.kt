package com.vaxapp.thingspeak.viewer.data.ip

import com.vaxapp.thingspeak.viewer.data.ApiService
import com.vaxapp.thingspeak.viewer.data.weather.WeatherResponse
import io.reactivex.Single

class IpCloudDataSource(private val service: ApiService) {
    fun fetchIp(): Single<IpResponse> {
        return service.fetchIp()
    }
}
