package com.shadi777.currency.rate.tracker.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shadi777.currency.rate.tracker.data.datasource.remote.CurrencyFreaksApi
import com.shadi777.currency.rate.tracker.data.datasource.remote.EconomiaAwesomeJsonApi
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.AvailableCurrenciesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.LatestRatesResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    const val FREAK_API_LABEL = "CurrencyFreaksApi"
    const val ECONOMIA_JSON_API_LABEL = "EconomiaJsonApi"

    @Singleton
    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(
            LatestRatesResponse::class.java,
            LatestRatesResponse.LatestRatesResponseDeserializer(),
        )
        .registerTypeAdapter(
            AvailableCurrenciesResponse::class.java,
            AvailableCurrenciesResponse.AvailableCurrenciesResponseDeserializer(),
        )
        .create()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideXmlConverterFactory(): SimpleXmlConverterFactory =
        SimpleXmlConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // ======================================================================
    // CURRENCY FREAKS API
    @Singleton
    @Provides
    @Named(FREAK_API_LABEL)
    fun provideRetrofitCurrencyFreakApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.currencyfreaks.com/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyFreaksApiService(@Named(FREAK_API_LABEL) retrofit: Retrofit): CurrencyFreaksApi =
        retrofit.create(CurrencyFreaksApi::class.java)

    // ======================================================================
    // ECONOMIA JSON API
    @Singleton
    @Provides
    @Named(ECONOMIA_JSON_API_LABEL)
    fun provideRetrofitEconomiaJsonApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br/json/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideEconomiaJsonApiService(@Named(ECONOMIA_JSON_API_LABEL) retrofit: Retrofit): EconomiaAwesomeJsonApi =
        retrofit.create(EconomiaAwesomeJsonApi::class.java)
}
