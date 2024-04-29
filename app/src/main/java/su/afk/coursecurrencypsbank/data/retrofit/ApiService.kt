package su.afk.coursecurrencypsbank.data.retrofit

import retrofit2.http.GET
import su.afk.coursecurrencypsbank.data.models.CurrencyResponse

interface ApiService {

    @GET("daily_json.js")
    suspend fun getCurrencyData(): CurrencyResponse
}