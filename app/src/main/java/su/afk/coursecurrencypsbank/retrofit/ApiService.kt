package su.afk.coursecurrencypsbank.retrofit

import retrofit2.http.GET

interface ApiService {
    @GET("daily_json.js")
    suspend fun getCurrencyData(): CurrencyResponse
}