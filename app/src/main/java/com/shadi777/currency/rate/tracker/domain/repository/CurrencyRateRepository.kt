package com.shadi777.currency.rate.tracker.domain.repository

import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRateRepository {
    suspend fun fetchCurrencyRateList(): Flow<ApiResult<List<CurrencyRateEntity>>>
}
