package com.shadi777.currency.rate.tracker.common

import retrofit2.Response

abstract class BaseApi {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResult.Success(it)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (ex: Exception) {
            return error(ex.message ?: ex.toString())
        }
    }

    private fun <T> error(errorMessage: String): ApiResult<T> = ApiResult.Error(errorMessage)
}
