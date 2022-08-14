package com.mechamanul.cocktaildb.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentOnboardingThirdScreenBinding

class FragmentThirdScreen : Fragment(R.layout.fragment_onboarding_third_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentOnboardingThirdScreenBinding.bind(view)
        binding.apply {
            finishButton.setOnClickListener {
                setOnBoardingCompleted()
                findNavController().navigate(R.id.action_fragmentOnboarding_to_fragmentCocktail)
            }
        }
    }

    private fun setOnBoardingCompleted() {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(getString(R.string.is_on_boarding_completed), true)
        editor.apply()
    }
}