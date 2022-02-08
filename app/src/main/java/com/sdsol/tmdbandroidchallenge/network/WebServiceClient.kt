package com.sdsol.tmdbandroidchallenge.network

import com.sdsol.tmdbandroidchallenge.BuildConfig
import com.sdsol.tmdbandroidchallenge.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServiceClient {
    companion object {
        var networkClient: BackEndApi? = null
        fun getClient(): BackEndApi? {
            if (networkClient == null) {
                val retrofitClient = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .client(
                        OkHttpClient.Builder()
                            .readTimeout(15, TimeUnit.SECONDS)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor { chain ->
                            val url = chain
                                .request()
                                .url
                                .newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build()
                            chain.proceed(chain.request().newBuilder().url(url).build())
                        }.build()
                    ).build()

                networkClient = retrofitClient.create(BackEndApi::class.java)
            }
            return networkClient
        }
    }
}
