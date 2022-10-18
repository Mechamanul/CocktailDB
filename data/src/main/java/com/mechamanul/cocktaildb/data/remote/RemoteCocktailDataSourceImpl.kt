package com.mechamanul.cocktaildb.data.remote

import com.google.gson.JsonParseException
import com.mechamanul.cocktaildb.data.remote.api.CocktailApi
import com.mechamanul.cocktaildb.data.repository.RemoteCocktailDataSource
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.ConnectionException
import com.mechamanul.cocktaildb.utils.EmptyRetrofitResultException
import com.mechamanul.cocktaildb.utils.JsonDeserializationException
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteCocktailDataSourceImpl @Inject constructor(
    private val cocktailApi: CocktailApi
) : RemoteCocktailDataSource {
    override suspend fun getRandomCocktail(): Cocktail = wrapRetrofitExceptions {

        val response = cocktailApi.getRandomCocktail()
        response.cocktails?.let {
            it[0]
        }
        throw EmptyRetrofitResultException("drinks[] is empty")
    }


    override suspend fun searchCocktailByName(name: String): List<Cocktail> =
        wrapRetrofitExceptions {
            val response = cocktailApi.searchCocktailByName(name)
            response.cocktails
                ?: throw EmptyRetrofitResultException("Drinks with given name was not found")
        }

    override suspend fun getListOfCategories(): List<String> = wrapRetrofitExceptions {
        cocktailApi.getListOfCategories().categories.map { it.name }
    }

    override suspend fun getCocktailsByCategoryName(categoryName: String): List<Cocktail> =
        wrapRetrofitExceptions {
            cocktailApi.getCocktailByCategoryName(categoryName).cocktails
                ?: throw EmptyRetrofitResultException("Somehow cocktails with this category do not exist")
        }

    private suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: AppException) {
            throw e
        } catch (e: JsonParseException) {
            throw JsonDeserializationException(e.message.toString())
        } catch (e: UnknownHostException) {
            throw ConnectionException(e)
        } catch (e: HttpException) {
            throw ConnectionException(e)
        }
    }

}