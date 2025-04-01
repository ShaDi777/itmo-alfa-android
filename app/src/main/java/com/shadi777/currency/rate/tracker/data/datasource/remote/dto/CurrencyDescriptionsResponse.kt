package com.shadi777.currency.rate.tracker.data.datasource.remote.dto

data class CurrencyDescriptionsResponse(
    val supportedCurrenciesMap: Map<String, Description>
) {
    data class Description(
        val currencyCode: String,
        val currencyName: String,
        val icon: String,
    )
}
