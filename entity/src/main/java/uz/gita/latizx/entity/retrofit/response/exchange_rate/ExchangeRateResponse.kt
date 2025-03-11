package uz.gita.latizx.entity.retrofit.response.exchange_rate

data class ExchangeRateResponse(
    val Ccy: String,
    val CcyNm_EN: String,
    val CcyNm_RU: String,
    val CcyNm_UZ: String,
    val CcyNm_UZC: String,
    val Code: String,
    val Date: String,
    val Diff: String,
    val Nominal: String,
    val Rate: String,
    val id: Int
)