package com.vaxapp.thingspeak.viewer.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.vaxapp.thingspeak.viewer.BuildConfig
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("https://api.thingspeak.com/channels/$CHANNEL_ID/feeds.json?results=1&api_key=${BuildConfig.THINGSPEAK_API_KEY}")
    fun fetchFields(): Single<ApiResponse>

    companion object {

        private const val CHANNEL_ID = 2405092
        private const val CHANNEL_ID_OLD = 298096

        private val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
            } else {
                originalResponse
            }
        }

        fun create(context: Context): ApiService {

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)
            val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    // Specify the cache we created earlier.
                    .cache(myCache)
                    // Add an Interceptor to the OkHttpClient.
                    .addInterceptor { chain ->

                        /*
                        *  We initialize the request and change its header depending on whether
                        *  the device is connected to Internet or not.
                        */
                        val request = when {
                            hasNetwork(context) -> createRecentCacheRequest(chain.request())
                            else -> createOlderCacheRequest(chain.request())
                        }

                        // Add the modified request to the chain.
                        chain.proceed(request)
                    }
                    .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.thingspeak.com/channels/$CHANNEL_ID/")
                    .client(client)
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }

        /*
         *  If there is no Internet, get the cache that was stored 7 days ago.
         *  If the cache is older than 7 days, then discard it,
         *  and indicate an error in fetching the response.
         *  The 'max-stale' attribute is responsible for this behavior.
         *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
         */
        private fun createOlderCacheRequest(request: Request): Request {
            return request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .build()
        }

        /**
         * If there is Internet, get the cache that was stored 5 seconds ago.
         *  If the cache is older than 5 seconds, then discard it,
         *  and indicate an error in fetching the response.
         *  The 'max-age' attribute is responsible for this behavior.
         */
        private fun createRecentCacheRequest(request: Request): Request {
            return request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5)
                    .build()
        }

        private fun hasNetwork(context: Context): Boolean {
            var isConnected = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) {
                isConnected = true
            }
            return isConnected
        }
    }
}
