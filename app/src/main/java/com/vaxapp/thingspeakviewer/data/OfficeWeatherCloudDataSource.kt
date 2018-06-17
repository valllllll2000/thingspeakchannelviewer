package com.vaxapp.thingspeakviewer.data

import io.reactivex.Single


class OfficeWeatherCloudDataSource(private val service: ApiService) {
    fun fetchFields(): Single<ApiResponse> {
        return service.fetchFields()
    }
}
