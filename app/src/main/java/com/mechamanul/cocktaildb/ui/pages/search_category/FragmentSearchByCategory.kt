package com.mechamanul.cocktaildb.ui.pages.search_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mechamanul.cocktaildb.databinding.FragmentSearchByCategoryBinding
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.elements.adapters.CategoriesAdapter
import com.mechamanul.cocktaildb.ui.elements.adapters.NavigateToCategory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSearchByCategory : BaseFragment(), NavigateToCategory {
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
        val adapter = CategoriesAdapter(listOf(), this)
        binding.apply {
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is Result.Error -> handleExceptions(
                            it.exception,
                            binding.root
                        ).show()
                        is Result.Success -> {

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

    override fun navigate(categoryName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val action =
                FragmentSearchByCategoryDirections.actionFragmentSearchByCategoryToFragmentCocktailsInCategory(
                    categoryName
                )
            findNavController().navigate(action)
        }
    }
}