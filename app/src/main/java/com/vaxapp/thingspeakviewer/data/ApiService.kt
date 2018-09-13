package com.vaxapp.thingspeakviewer.data

import com.vaxapp.thingspeakviewer.BuildConfig
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    //"https://api.thingspeak.com/channels/298096/feeds.json?results=1"
    @GET("https://api.thingspeak.com/channels/298096/feeds.json?results=1")
    fun fetchFields(/*@Query("results") results: Int = 1*/): Single<ApiResponse>

    companion object {

        fun create(): ApiService {

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.thingspeak.com/channels/298096/")
                    .client(client)
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}