package com.shadi777.currency.rate.tracker.domain.usecase

import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository

class CurrencyRateUseCase(private val currencyRateRepository: CurrencyRateRepository) {
    suspend fun fetchAvailableCurrency() = currencyRateRepository.fetchAvailableCurrencies()

    suspend fun fetchRates(baseCurrency: String, otherCurrency: List<String>) =
        currencyRateRepository.fetchCurrencyRateList(baseCurrency, otherCurrency)
}
