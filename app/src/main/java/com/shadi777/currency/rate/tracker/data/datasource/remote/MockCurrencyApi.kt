package com.shadi777.currency.rate.tracker.data.datasource.remote

import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyRateResponse
import retrofit2.Response
import retrofit2.http.*

interface MockCurrencyApi {
    @GET("/currency")
    suspend fun getCurrencyRates(): Response<List<CurrencyRateResponse>>
}
