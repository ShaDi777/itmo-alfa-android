package com.shadi777.currency.rate.tracker.domain.entity

data class CurrencyRateEntity(
    val baseCurrency: String,
    val otherCurrency: String,
    val rate: Double,
    val timestamp: Long,
)
