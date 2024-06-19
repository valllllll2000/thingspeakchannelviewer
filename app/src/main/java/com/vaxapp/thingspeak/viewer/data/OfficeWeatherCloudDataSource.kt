package com.vaxapp.thingspeak.viewer.data

import io.reactivex.Single

class OfficeWeatherCloudDataSource(private val service: ApiService) {
    fun fetchFields(): Single<ApiResponse> {
        return service.fetchFields()
    }
}
