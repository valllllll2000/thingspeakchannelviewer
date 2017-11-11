package com.vaxapp.thingspeakviewer.data

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by valeria on 11/11/17.
 */
interface ApiService {

    //"https://api.thingspeak.com/channels/298096/feeds.json?results=1"
    @GET
    fun fetchFields(@Url url: String): Observable<ApiResponse>

    companion object {

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://api.thingspeak.com")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}