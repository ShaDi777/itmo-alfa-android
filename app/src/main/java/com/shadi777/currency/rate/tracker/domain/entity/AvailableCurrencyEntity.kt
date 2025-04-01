package com.shadi777.currency.rate.tracker.domain.entity

data class AvailableCurrencyEntity(
    val shortCode: String,
    val fullCode: String,
    val iconUrl: String,
    val exchangePossibleCurrencyCodes: List<String>
)
