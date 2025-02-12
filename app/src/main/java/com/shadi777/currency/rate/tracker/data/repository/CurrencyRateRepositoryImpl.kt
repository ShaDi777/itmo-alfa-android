package com.shadi777.currency.rate.tracker.data.repository

import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.common.BaseApi
import com.shadi777.currency.rate.tracker.data.datasource.CurrencyRateDataSource
import com.shadi777.currency.rate.tracker.data.datasource.remote.dto.CurrencyRateResponse
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CurrencyRateRepositoryImpl(
    private val storiesDataSource: CurrencyRateDataSource
) : BaseApi(), CurrencyRateRepository {
    override suspend fun fetchCurrencyRateList(): Flow<ApiResult<List<CurrencyRateEntity>>> {
        return flow {
            emit(
                mapApiResult(
                    safeApiCall {
                        storiesDataSource.getCurrencyRateList()
                    }
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    private fun mapApiResult(oldResult: ApiResult<List<CurrencyRateResponse>>): ApiResult<List<CurrencyRateEntity>> {
        return when (oldResult) {
            is ApiResult.Success -> ApiResult.Success(oldResult.data!!.map { it.mapToEntity() })
            is ApiResult.Error -> ApiResult.Error(
                oldResult.message!!,
                oldResult.data?.map { it.mapToEntity() })

            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Nothing -> ApiResult.Nothing()
        }
    }
}

