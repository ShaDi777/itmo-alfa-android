package com.shadi777.currency.rate.tracker.data.datasource.remote.dto

import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity

data class CurrencyRateResponse(
    val rate: Double,
    val currencyName: String,
    val currencyCode: String,
) {
    fun mapToEntity() = CurrencyRateEntity(
        fullLabel = currencyName,
        shortLabel = currencyCode,
        rate = rate,
    )
}
