package com.shadi777.currency.rate.tracker.data.datasource.remote.dto

abstract class CurrencyRateDto {
    abstract val high: String
    abstract val low: String
    abstract val bid: String
    abstract val ask: String
    abstract val timestamp: String
}

data class SimpleCurrencyRateDto(
    override val high: String,
    override val low: String,
    override val bid: String,
    override val ask: String,
    override val timestamp: String,
) : CurrencyRateDto()

data class NamedCurrencyRateDto(
    val code: String,
    val codein: String,
    override val high: String,
    override val low: String,
    override val bid: String,
    override val ask: String,
    override val timestamp: String,
) : CurrencyRateDto()
