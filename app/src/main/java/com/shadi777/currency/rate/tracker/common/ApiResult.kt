package com.shadi777.currency.rate.tracker.common

sealed class ApiResult<T>(
    val data: T? = null,
    val message: String? = null,
    val responseCode: String? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)

    class Error<T>(message: String, data: T? = null) : ApiResult<T>(data, message)

    class Loading<T> : ApiResult<T>()

    class Nothing<T> : ApiResult<T>()
}
