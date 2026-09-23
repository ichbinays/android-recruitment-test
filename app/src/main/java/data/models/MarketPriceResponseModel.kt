package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketPriceResponseModel(
    @SerialName("symbol") val symbol: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("timestamp") val timestamp: Long? = null,
    @SerialName("changePrice") val changePrice: Double? = null,
    @SerialName("changePercent") val changePercent: Double? = null
)
