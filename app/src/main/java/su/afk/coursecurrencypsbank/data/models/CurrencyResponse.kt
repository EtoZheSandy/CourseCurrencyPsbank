package su.afk.coursecurrencypsbank.data.models

data class CurrencyResponse(
    val Date: String,
    val PreviousDate: String,
    val Timestamp: String,
    val Valute: Map<String, Currency>,
)
