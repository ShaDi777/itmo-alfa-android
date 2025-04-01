package com.shadi777.currency.rate.tracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.domain.usecase.CurrencyRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CurrencyRateViewModel @Inject constructor(
    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel() {
    private val _currencyRateLiveData = MutableStateFlow<ApiResult<List<CurrencyDisplayData>>>(ApiResult.Loading())
    val currencyRateLiveData: StateFlow<ApiResult<List<CurrencyDisplayData>>> = _currencyRateLiveData

    init {
        Log.i("RecreateViewModel", "new constructor call")
        refreshExchangeRates("USD")
    }

    fun refreshExchangeRates(baseCurrency: String) {
        viewModelScope.launch {
            try {
                val availableCurrenciesResponse = currencyRateUseCase.fetchAvailableCurrency()

                if (availableCurrenciesResponse.first().data == null) {
                    _currencyRateLiveData.value =
                        ApiResult.Error("Ошибка загрузки валют", null)
                    return@launch
                }

                val availableCurrencies = availableCurrenciesResponse.first().data!!

                val ratesResponse =
                    currencyRateUseCase.fetchRates(
                        baseCurrency,
                        availableCurrencies.first { it.shortCode == baseCurrency }.exchangePossibleCurrencyCodes
                    )

                if (ratesResponse.first().data == null) {
                    _currencyRateLiveData.value =
                        ApiResult.Error("Ошибка загрузки курсов валют", null)
                    return@launch
                }

                val currencyRates = ratesResponse.first().data!!
                _currencyRateLiveData.value = ApiResult.Success(
                    currencyRates
                        .filter { currency ->
                            availableCurrencies.firstOrNull { it.shortCode == currency.otherCurrency } != null
                        }
                        .map { currency ->
                            val description =
                                availableCurrencies.firstOrNull { it.shortCode == currency.otherCurrency }
                            CurrencyDisplayData(
                                shortCode = currency.otherCurrency,
                                fullName = description?.fullCode ?: "",
                                rate = (currency.rate * 100).roundToInt() / 100.0,
                                iconUrl = description?.iconUrl ?: "",
                            )
                        }
                )

            } catch (e: Exception) {
                _currencyRateLiveData.value = ApiResult.Error("Ошибка: ${e.message}")
            }
        }
    }
}

