package su.afk.coursecurrencypsbank.retrofit

data class CurrencyResponse(
    val Date: String,
    val PreviousDate: String,
    val Timestamp: String,
    val Valute: Map<String, Currency>,
)
