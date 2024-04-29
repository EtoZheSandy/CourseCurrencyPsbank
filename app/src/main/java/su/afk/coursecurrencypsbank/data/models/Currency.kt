package su.afk.coursecurrencypsbank.data.models

data class Currency(
    val ID: String,
    val NumCode: String,
    val CharCode: String,
    val Nominal: Int,
    val Name: String,
    val Value: Double,
    val Previous: Double,
)