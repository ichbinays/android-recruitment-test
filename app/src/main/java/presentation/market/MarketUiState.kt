package presentation.market

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class MarketUiState(
    val symbol: String,
    val formattedPrice: String,
    val formattedTimestamp: String,
    val formattedChange: String,
    @ColorRes val changeColor: Int,
    @DrawableRes val changeBackground: Int
)
