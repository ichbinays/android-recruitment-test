package data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_items")
data class MarketEntity(
    @PrimaryKey val symbol: String,
    val price: Double,
    val timestamp: Long,
    val changePrice: Double,
    val changePercent: Double
)
