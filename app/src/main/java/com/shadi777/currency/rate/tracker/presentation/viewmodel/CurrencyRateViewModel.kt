package com.shadi777.currency.rate.tracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import com.shadi777.currency.rate.tracker.domain.usecase.CurrencyRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyRateViewModel @Inject constructor(
    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel() {
    private val responseCurrencyRate: MutableLiveData<ApiResult<List<CurrencyRateEntity>>> =
        MutableLiveData()
    val currencyRateLiveData: LiveData<ApiResult<List<CurrencyRateEntity>>> = responseCurrencyRate

    fun getCurrencyRates() = viewModelScope.launch {
        responseCurrencyRate.value = ApiResult.Loading()

        currencyRateUseCase.fetchFromRemoteApi()
            .collect { values ->
                responseCurrencyRate.value = values
            }
    }
}
