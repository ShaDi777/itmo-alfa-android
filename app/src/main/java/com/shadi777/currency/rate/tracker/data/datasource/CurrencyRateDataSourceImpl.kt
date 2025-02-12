package com.shadi777.currency.rate.tracker.data.datasource

import com.shadi777.currency.rate.tracker.data.datasource.remote.MockCurrencyApi
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyRateResponse
import retrofit2.Response

class CurrencyRateDataSourceImpl(private var api: MockCurrencyApi) : CurrencyRateDataSource {
    override suspend fun getCurrencyRateList(): Response<List<CurrencyRateResponse>> {
        return api.getCurrencyRates()
    }
}
