package com.sdsol.tmdbandroidchallenge.appmodule

import com.sdsol.tmdbandroidchallenge.BuildConfig
import com.sdsol.tmdbandroidchallenge.network.BackEndApi
import com.sdsol.tmdbandroidchallenge.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
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

    @Provides
    fun provideBaseURL() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, baseURL : String) : BackEndApi {
        val retrofitClient = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .client(okHttpClient).build()

        return retrofitClient.create(BackEndApi::class.java)
    }
}