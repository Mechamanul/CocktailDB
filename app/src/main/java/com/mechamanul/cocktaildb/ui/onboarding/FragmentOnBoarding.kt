package com.mechamanul.cocktaildb.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentOnboardingBinding
import com.mechamanul.cocktaildb.ui.onboarding.screens.FragmentFirstScreen
import com.mechamanul.cocktaildb.ui.onboarding.screens.FragmentSecondScreen
import com.mechamanul.cocktaildb.ui.onboarding.screens.FragmentThirdScreen


class FragmentOnBoarding : Fragment(R.layout.fragment_onboarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (onBoardingCompleted()) {
            findNavController().navigate(R.id.action_fragmentOnboarding_to_fragmentCocktail)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        val binding: FragmentOnboardingBinding = FragmentOnboardingBinding.bind(view)

        val fragmentsList = arrayListOf<Fragment>(
            FragmentFirstScreen(), FragmentSecondScreen(), FragmentThirdScreen()
        )
        val adapter = OnBoardingViewPagerAdapter(fragmentsList, parentFragmentManager, lifecycle)
        binding.apply {
            viewPager.adapter = adapter
        }
        return view
    }

    private fun onBoardingCompleted(): Boolean {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return preferences.getBoolean(getString(R.string.is_on_boarding_completed),false)

    }
}