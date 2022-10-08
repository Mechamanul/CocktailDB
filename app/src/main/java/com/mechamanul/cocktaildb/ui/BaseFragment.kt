package com.mechamanul.cocktaildb.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.ConnectionException
import com.mechamanul.cocktaildb.utils.EmptyRetrofitResultException
import com.mechamanul.cocktaildb.utils.JsonDeserializationException

open class BaseFragment : Fragment() {
    /**
     * handles all app exceptions apart from the connectivity ones
     */
    fun handleExceptions(e: AppException, viewRoot: View): Snackbar {
        val message: String = when (e) {
            is EmptyRetrofitResultException -> e.message ?: "Api call returned nothing"
            is JsonDeserializationException -> e.message ?: "Json deserialization error"
            else -> e.message ?: "Unhandled exception"
        }

        return Snackbar.make(
            viewRoot,
            message,
            Snackbar.LENGTH_LONG
        )
    }
}