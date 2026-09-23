package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {

    @Query("SELECT * FROM market_items WHERE symbol != 'UNKNOWN' ORDER BY symbol ASC")
    fun getAllMarketItems(): Flow<List<MarketEntity>>

    @Query("SELECT * FROM market_items WHERE symbol = :symbol")
    suspend fun getMarketItemBySymbol(symbol: String): MarketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketItems(items: List<MarketEntity>)
}
