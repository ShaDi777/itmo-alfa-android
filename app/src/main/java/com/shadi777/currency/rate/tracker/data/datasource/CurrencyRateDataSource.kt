package com.shadi777.currency.rate.tracker.data.datasource

import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyRateResponse
import retrofit2.Response

interface CurrencyRateDataSource {
    suspend fun getCurrencyRateList(): Response<List<CurrencyRateResponse>>
}
