package com.shadi777.currency.rate.tracker.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.databinding.FragmentCurrencyRateBinding
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity
import com.shadi777.currency.rate.tracker.presentation.adapter.CurrencyRateDataAdapter
import com.shadi777.currency.rate.tracker.presentation.viewmodel.CurrencyRateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyRateFragment : Fragment() {
    private val viewModel: CurrencyRateViewModel by viewModels<CurrencyRateViewModel>()
    private var _binding: FragmentCurrencyRateBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CurrencyRateDataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyRateDataObserver()
        viewModel.getCurrencyRates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyRateBinding.inflate(inflater, container, false)

        binding.btnSettings.setOnClickListener {
            val resultFragment = SettingsFragment()
            resultFragment.show(parentFragmentManager, resultFragment.tag)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun currencyRateDataObserver() {
        viewModel.currencyRateLiveData.observe(this) { response ->
            when (response) {
                is ApiResult.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }

                is ApiResult.Success -> {
                    if (!response.data.isNullOrEmpty()) {
                        setAdapter(response.data)
                    }
                    binding.loadingBar.visibility = View.INVISIBLE
                }

                is ApiResult.Error -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                }

                is ApiResult.Nothing -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setAdapter(products: List<CurrencyRateEntity>) {
        adapter = CurrencyRateDataAdapter()
        adapter.submitData(products.toMutableList())
        binding.rvCurrencyRatesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCurrencyRatesList.adapter = adapter
    }
}
