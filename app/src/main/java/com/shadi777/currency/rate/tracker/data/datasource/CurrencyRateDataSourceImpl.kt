package com.shadi777.currency.rate.tracker.data.datasource

import com.shadi777.currency.rate.tracker.data.datasource.remote.CurrencyFreaksApi
import com.shadi777.currency.rate.tracker.data.datasource.remote.EconomiaAwesomeJsonApi
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.AvailableCurrenciesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyDescriptionsResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.LatestRatesResponse
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.SimpleCurrencyRateDto
import retrofit2.Response

class CurrencyRateDataSourceImpl(
    private val freaksApi: CurrencyFreaksApi,
    private val mainJsonApi: EconomiaAwesomeJsonApi,
) : CurrencyRateDataSource {
    override suspend fun getCurrencyRateList(
        baseCurrencyCode: String,
        otherCurrencyCodes: Collection<String>,
    ): Response<LatestRatesResponse> {
        val request = otherCurrencyCodes.joinToString(separator = ",") { "$baseCurrencyCode-$it" }
        return mainJsonApi.getLatestRates(request)
    }

    override suspend fun getAvailableCurrencies(): Response<AvailableCurrenciesResponse> {
        return mainJsonApi.getAvailableCurrencies()
    }

    override suspend fun getDescriptionForCurrencies(): Response<CurrencyDescriptionsResponse> {
        return freaksApi.getAllCurrencyDescriptions()
    }

    override suspend fun getRatesByMinutes(
        baseCurrencyCode: String,
        otherCurrencyCode: String,
        lastMinutes: Int
    ): Response<List<SimpleCurrencyRateDto>> {
        return mainJsonApi.getTimeSeriesByMinute(
            "$baseCurrencyCode-$otherCurrencyCode",
            lastMinutes
        )
    }

    override suspend fun getRatesByDays(
        baseCurrencyCode: String,
        otherCurrencyCode: String,
        lastDays: Int
    ): Response<List<SimpleCurrencyRateDto>> {
        return mainJsonApi.getTimeSeriesByDay(
            "$baseCurrencyCode-$otherCurrencyCode",
            lastDays
        )
    }
}
