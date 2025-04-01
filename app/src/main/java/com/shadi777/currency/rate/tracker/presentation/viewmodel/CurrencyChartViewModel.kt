package com.shadi777.currency.rate.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shadi777.currency.rate.tracker.domain.usecase.TimeRangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencyChartViewModel @Inject constructor(
    private val timeRangeUseCase: TimeRangeUseCase
) : ViewModel() {

    private val _chartData = MutableStateFlow<List<Pair<Long, Double>>>(emptyList())
    val chartData: StateFlow<List<Pair<Long, Double>>> = _chartData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun loadDataForRange(range: String, baseCurrency: String, targetCurrency: String) {
        _isLoading.value = true

        val data = when (range) {
            "day" -> timeRangeUseCase.getDayInfo(baseCurrency, targetCurrency)
            "week" -> timeRangeUseCase.getWeekInfo(baseCurrency, targetCurrency)
            "month" -> timeRangeUseCase.getMonthInfo(baseCurrency, targetCurrency)
            "half_year" -> timeRangeUseCase.getHalfYearInfo(baseCurrency, targetCurrency)
            "year" -> timeRangeUseCase.getYearInfo(baseCurrency, targetCurrency)
            else -> emptyList()
        }

        _chartData.value = data
        _isLoading.value = false
    }
}

