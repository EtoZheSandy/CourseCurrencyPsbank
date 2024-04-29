package su.afk.coursecurrencypsbank.data

import dagger.hilt.android.scopes.ActivityScoped
import su.afk.coursecurrencypsbank.data.models.CurrencyResponse
import su.afk.coursecurrencypsbank.data.retrofit.ApiService
import javax.inject.Inject

@ActivityScoped
class CourceRepository @Inject constructor(
    private val api: ApiService
){
//    suspend fun getCource(): CurrencyResponse {
//
//    }
}