package com.mechamanul.cocktaildb.ui.pages.search_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mechamanul.cocktaildb.databinding.FragmentSearchByCategoryBinding
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.elements.adapters.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSearchByCategory : BaseFragment() {
    val viewModel: SearchByCategoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchByCategoryBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = FragmentSearchByCategoryBinding.bind(view)
        val adapter = CategoriesAdapter(listOf())
        binding.apply {
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is SearchByCategoryViewModel.CategoriesUiState.Error -> handleExceptions(
                            it.e,
                            binding.root
                        ).show()
                        is SearchByCategoryViewModel.CategoriesUiState.Success -> {

                            adapter.submitList(
                                it.data
                            )
                        }
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}