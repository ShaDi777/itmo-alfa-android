package com.shadi777.currency.rate.tracker.data.datasource.remote.dto

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class LatestRatesResponse(
    val rates: Map<String, NamedCurrencyRateDto>
) {
    class LatestRatesResponseDeserializer : JsonDeserializer<LatestRatesResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): LatestRatesResponse {
            if (json == null) return LatestRatesResponse(emptyMap())

            val jsonObject = json.asJsonObject
            val ratesMap = mutableMapOf<String, NamedCurrencyRateDto>()

            jsonObject.entrySet().forEach { entry ->
                val key = entry.key
                val value = entry.value
                val exchangeRate: NamedCurrencyRateDto = context!!.deserialize(value, NamedCurrencyRateDto::class.java)
                ratesMap[key] = exchangeRate
            }

            return LatestRatesResponse(ratesMap)
        }

    }
}
