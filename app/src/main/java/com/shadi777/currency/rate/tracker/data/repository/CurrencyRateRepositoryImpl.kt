package com.shadi777.currency.rate.tracker.data.repository

import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.common.BaseApi
import com.shadi777.currency.rate.tracker.data.datasource.CurrencyRateDataSource
import com.shadi777.currency.rate.tracker.domain.entity.AvailableCurrencyEntity
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class CurrencyRateRepositoryImpl(
    private val currencyRateDataSource: CurrencyRateDataSource
) : BaseApi(), CurrencyRateRepository {
    override suspend fun fetchCurrencyRateList(
        baseCurrencyCode: String,
        otherCurrencyCodes: Collection<String>,
    ): Flow<ApiResult<List<CurrencyRateEntity>>> {
        return flow {
            emit(
                safeApiCall {
                    Response.success(
                        currencyRateDataSource.getCurrencyRateList(
                            baseCurrencyCode,
                            otherCurrencyCodes
                        ).body()?.rates?.values?.map {
                            CurrencyRateEntity(
                                it.code,
                                it.codein,
                                it.bid.toDouble(),
                                it.timestamp.toLong(),
                            )
                        }
                    )
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchAvailableCurrencies(): Flow<ApiResult<List<AvailableCurrencyEntity>>> {
        val info = safeApiCall { currencyRateDataSource.getAvailableCurrencies() }
        val description = safeApiCall { currencyRateDataSource.getDescriptionForCurrencies() }

        val mapCurrencyToPossibleOther = info.data!!.currencies.keys
            .map { it.split("-") }
            .groupBy { it.first() }
            .mapValues { entry ->
                entry.value.flatten().filter { it != entry.key }
            }

        val mapCurrencyDescription = description.data!!.supportedCurrenciesMap

        val result = mapCurrencyToPossibleOther
            .filter { mapCurrencyDescription.keys.contains(it.key) }
            .map {
                val desc = mapCurrencyDescription[it.key]!!
                AvailableCurrencyEntity(
                    shortCode = desc.currencyCode,
                    fullCode = desc.currencyName,
                    iconUrl = desc.icon,
                    exchangePossibleCurrencyCodes = it.value
                )
            }

        return flow {
            emit(
                ApiResult.Success(result)
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchRateByMinutes(
        base: String,
        other: String,
        lastNMinutes: Int
    ): ApiResult<List<CurrencyRateEntity>> {
        val apiResult = safeApiCall {
            currencyRateDataSource.getRatesByMinutes(base, other, lastNMinutes)
        }

        return if (apiResult is ApiResult.Success) {
            ApiResult.Success(
                apiResult.data?.map {
                    CurrencyRateEntity(
                        base,
                        other,
                        it.bid.toDouble(),
                        it.timestamp.toLong()
                    )
                } ?: emptyList()
            )
        } else {
            ApiResult.Error(message = apiResult.message ?: "Error")
        }
    }

    override suspend fun fetchRateByDays(
        base: String,
        other: String,
        lastNDays: Int
    ): ApiResult<List<CurrencyRateEntity>> {
        val apiResult = safeApiCall {
            currencyRateDataSource.getRatesByDays(base, other, lastNDays)
        }

        return if (apiResult is ApiResult.Success) {
            ApiResult.Success(
                apiResult.data?.map {
                    CurrencyRateEntity(
                        base,
                        other,
                        it.bid.toDouble(),
                        it.timestamp.toLong()
                    )
                } ?: emptyList()
            )
        } else {
            ApiResult.Error(message = apiResult.message ?: "Error")
        }
    }
}

