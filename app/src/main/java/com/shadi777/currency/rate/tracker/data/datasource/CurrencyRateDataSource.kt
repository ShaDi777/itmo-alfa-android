package com.shadi777.currency.rate.tracker.data.datasource

import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.AvailableCurrenciesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyDescriptionsResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.LatestRatesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.SimpleCurrencyRateDto
import retrofit2.Response

interface CurrencyRateDataSource {
    suspend fun getCurrencyRateList(
        baseCurrencyCode: String,
        otherCurrencyCodes: Collection<String>,
    ): Response<LatestRatesResponse>

    suspend fun getAvailableCurrencies(): Response<AvailableCurrenciesResponse>

    suspend fun getDescriptionForCurrencies(): Response<CurrencyDescriptionsResponse>

    suspend fun getRatesByMinutes(
        baseCurrencyCode: String,
        otherCurrencyCode: String,
        lastMinutes: Int,
    ): Response<List<SimpleCurrencyRateDto>>

    suspend fun getRatesByDays(
        baseCurrencyCode: String,
        otherCurrencyCode: String,
        lastDays: Int,
    ): Response<List<SimpleCurrencyRateDto>>
}
