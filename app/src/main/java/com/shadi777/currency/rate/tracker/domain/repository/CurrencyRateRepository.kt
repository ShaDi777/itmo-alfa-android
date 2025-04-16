package com.shadi777.currency.rate.tracker.domain.repository

import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.domain.entity.AvailableCurrencyEntity
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRateRepository {
    suspend fun fetchCurrencyRateList(
        baseCurrencyCode: String,
        otherCurrencyCodes: Collection<String>,
    ): Flow<ApiResult<List<CurrencyRateEntity>>>

    suspend fun fetchAvailableCurrencies(): Flow<ApiResult<List<AvailableCurrencyEntity>>>

    suspend fun fetchRateByMinutes(
        base: String,
        other: String,
        lastNMinutes: Int
    ): ApiResult<List<CurrencyRateEntity>>

    suspend fun fetchRateByDays(
        base: String,
        other: String,
        lastNDays: Int
    ): ApiResult<List<CurrencyRateEntity>>
}
