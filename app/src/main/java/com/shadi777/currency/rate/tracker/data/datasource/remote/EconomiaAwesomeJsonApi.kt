package com.shadi777.currency.rate.tracker.data.datasource.remote

import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.AvailableCurrenciesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.LatestRatesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.SimpleCurrencyRateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EconomiaAwesomeJsonApi {
    @GET("/last/{currencyPairsCommaSeparated}")
    suspend fun getLatestRates(
        @Path("currencyPairsCommaSeparated") currencyPairsCommaSeparated: String,
    ): Response<LatestRatesResponse>

    @GET("/{currencyPair}/{minutes}")
    suspend fun getTimeSeriesByMinute(
        @Path("currencyPair") currencyPair: String,
        @Path("minutes") lastNumberOfMinutes: Int,
    ): Response<List<SimpleCurrencyRateDto>>

    @GET("/daily/{currencyPair}/{days}")
    suspend fun getTimeSeriesByDay(
        @Path("currencyPair") currencyPair: String,
        @Path("days") lastNumberOfDays: Int,
    ): Response<List<SimpleCurrencyRateDto>>

    @GET("/available")
    suspend fun getAvailableCurrencies(): Response<AvailableCurrenciesResponse>
}
