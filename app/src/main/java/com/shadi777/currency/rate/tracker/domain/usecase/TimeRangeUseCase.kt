package com.shadi777.currency.rate.tracker.domain.usecase

import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository

class TimeRangeUseCase(private val currencyRateRepository: CurrencyRateRepository) {
    suspend fun getDayInfo(base: String, other: String): List<Pair<Long, Double>> =
        doRequest { currencyRateRepository.fetchRateByMinutes(base, other, 1440) }

    suspend fun getWeekInfo(base: String, other: String): List<Pair<Long, Double>> =
        doRequest { currencyRateRepository.fetchRateByDays(base, other, 7) }

    suspend fun getMonthInfo(base: String, other: String): List<Pair<Long, Double>> =
        doRequest { currencyRateRepository.fetchRateByDays(base, other, 31) }

    suspend fun getHalfYearInfo(base: String, other: String): List<Pair<Long, Double>> =
        doRequest { currencyRateRepository.fetchRateByDays(base, other, 180) }

    suspend fun getYearInfo(base: String, other: String): List<Pair<Long, Double>> =
        doRequest { currencyRateRepository.fetchRateByDays(base, other, 365) }

    private suspend fun doRequest(
        block: suspend () -> ApiResult<List<CurrencyRateEntity>>,
    ): List<Pair<Long, Double>> {
        val result = block().data ?: emptyList()
        return result.map { Pair(it.timestamp, it.rate) }
    }
}
