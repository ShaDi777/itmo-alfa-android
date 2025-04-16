package com.shadi777.currency.rate.tracker.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shadi777.currency.rate.tracker.databinding.FragmentCurrencyChartBinding
import com.shadi777.currency.rate.tracker.presentation.viewmodel.CurrencyChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyChartFragment : Fragment() {

    private val viewModel: CurrencyChartViewModel by viewModels()
    private var _binding: FragmentCurrencyChartBinding? = null
    private val binding get() = _binding!!

    private val baseCurrencyCode: String = "USD"
    private lateinit var currencyCode: String
    private lateinit var selectedRange: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyCode = arguments?.getString("currencyCode") ?: return

        binding.tvChartName.text = "$baseCurrencyCode - $currencyCode"
        initRadioGroup()

        lifecycleScope.launch {
            viewModel.chartData.collectLatest { data ->
                binding.customChartView.setData(data, selectedRange)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initRadioGroup() {
        binding.radioGroupTimeRange.check(binding.rbHalfYear.id)
        selectedRange = "half_year"
        lifecycleScope.launch {
            viewModel.loadDataForRange(selectedRange, baseCurrencyCode, currencyCode)
        }

        binding.radioGroupTimeRange.isSelectionRequired = true

        binding.radioGroupTimeRange.addOnButtonCheckedListener { _, id, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            selectedRange = when (id) {
                binding.rbDay.id -> "day"
                binding.rbWeek.id -> "week"
                binding.rbMonth.id -> "month"
                binding.rbHalfYear.id -> "half_year"
                binding.rbYear.id -> "year"
                else -> throw RuntimeException("Unsupported type")
            }

            lifecycleScope.launch {
                viewModel.loadDataForRange(selectedRange, baseCurrencyCode, currencyCode)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
