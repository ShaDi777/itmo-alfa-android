package com.shadi777.currency.rate.tracker.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shadi777.currency.rate.tracker.databinding.FragmentSettingsBinding
import com.shadi777.currency.rate.tracker.presentation.utils.ThemeMode

class SettingsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initThemeModeSelection()

        return binding.root
    }

    private fun initThemeModeSelection() {
        val currentMode = ThemeMode.getCurrentMode(requireContext())

        binding.modeSelection.check(
            when (currentMode) {
                ThemeMode.LIGHT -> binding.lightMode.id
                ThemeMode.DARK -> binding.darkMode.id
                ThemeMode.SYSTEM -> binding.systemMode.id
            }
        )

        binding.modeSelection.isSelectionRequired = true

        binding.modeSelection.addOnButtonCheckedListener { _, id, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (id) {
                binding.lightMode.id -> ThemeMode.setCurrentMode(requireContext(), ThemeMode.LIGHT)
                binding.darkMode.id -> ThemeMode.setCurrentMode(requireContext(), ThemeMode.DARK)
                binding.systemMode.id -> ThemeMode.setCurrentMode(
                    requireContext(),
                    ThemeMode.SYSTEM
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
