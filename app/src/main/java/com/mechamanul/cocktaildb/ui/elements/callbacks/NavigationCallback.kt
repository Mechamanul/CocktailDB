package com.mechamanul.cocktaildb.ui.elements.callbacks

import com.mechamanul.cocktaildb.domain.model.Cocktail
import kotlinx.coroutines.Job

interface NavigationCallback {
    fun navigateToCocktailDetails(cocktail: Cocktail): Job
}