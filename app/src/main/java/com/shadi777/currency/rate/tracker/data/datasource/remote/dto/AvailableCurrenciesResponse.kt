package com.shadi777.currency.rate.tracker.data.datasource.remote.dto

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class AvailableCurrenciesResponse(
    val currencies: Map<String, String>
) {
    class AvailableCurrenciesResponseDeserializer : JsonDeserializer<AvailableCurrenciesResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): AvailableCurrenciesResponse {
            if (json == null) return AvailableCurrenciesResponse(emptyMap())

            val jsonObject = json.asJsonObject
            val map = mutableMapOf<String, String>()

            jsonObject.entrySet().forEach { entry ->
                val key = entry.key
                val value = entry.value
                val string: String = context!!.deserialize(value, String::class.java)
                map[key] = string
            }

            return AvailableCurrenciesResponse(map)
        }

    }
}

