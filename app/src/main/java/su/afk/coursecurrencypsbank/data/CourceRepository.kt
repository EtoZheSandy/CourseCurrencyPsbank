package su.afk.coursecurrencypsbank.data

import dagger.hilt.android.scopes.ActivityScoped
import su.afk.coursecurrencypsbank.data.models.CurrencyResponse
import su.afk.coursecurrencypsbank.data.retrofit.ApiService
import su.afk.coursecurrencypsbank.util.Resource
import javax.inject.Inject

@ActivityScoped
class CourceRepository @Inject constructor(
    private val api: ApiService
){

    suspend fun getCource(): Resource<CurrencyResponse> {
        val response = try {
            api.getCurrencyData()
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }
}