package com.shadi777.currency.rate.tracker.data.datasource.remote

import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyDescriptionsResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyFreaksApi {
    @GET("/v2.0/supported-currencies")
    suspend fun getAllCurrencyDescriptions(): Response<CurrencyDescriptionsResponse>
}
