package com.shadi777.currency.rate.tracker.domain.usecase

import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository

class CurrencyRateUseCase(private val currencyRateRepository: CurrencyRateRepository) {
    suspend fun fetchFromRemoteApi() = currencyRateRepository.fetchCurrencyRateList()
}
