package com.shadi777.currency.rate.tracker.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadi777.currency.rate.tracker.R
import com.shadi777.currency.rate.tracker.common.ApiResult
import com.shadi777.currency.rate.tracker.databinding.FragmentCurrencyRateBinding
import com.shadi777.currency.rate.tracker.presentation.adapter.CurrencyRateDataAdapter
import com.shadi777.currency.rate.tracker.presentation.viewmodel.CurrencyDisplayData
import com.shadi777.currency.rate.tracker.presentation.viewmodel.CurrencyRateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyRateFragment : Fragment() {
    private val viewModel: CurrencyRateViewModel by viewModels<CurrencyRateViewModel>()
    private var _binding: FragmentCurrencyRateBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CurrencyRateDataAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyRateDataObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun currencyRateDataObserver() {
        lifecycleScope.launch {
            viewModel.currencyRateLiveData.collect { response ->
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
                        Toast.makeText(
                            this@CurrencyRateFragment.requireContext(),
                            response.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        binding.loadingBar.visibility = View.INVISIBLE
                    }

                    is ApiResult.Nothing -> {
                        binding.loadingBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun setAdapter(products: List<CurrencyDisplayData>) {
        adapter = CurrencyRateDataAdapter { currencyCode ->
            findNavController().navigate(
                R.id.action_currencyRateFragment_to_currencyChartFragment,
                bundleOf("currencyCode" to currencyCode)
            )
        }
        adapter.submitData(products.toMutableList())
        binding.rvCurrencyRatesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCurrencyRatesList.adapter = adapter
    }
}
