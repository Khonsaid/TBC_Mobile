package uz.gita.latizx.comman.model

data class ExchangeRateModel(
    val ccy: String,
    val ccyNmEN: String,
    val ccyNmRU: String,
    val ccyNmUZ: String,
    val ccyNmUZC: String,
    val code: String,
    val date: String,
    val diff: String,
    val nominal: String,
    val rate: String,
    val id: Int
)