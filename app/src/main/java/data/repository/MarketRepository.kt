package data.repository

import data.local.MarketEntity
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getLocalMarketItems(): Flow<List<MarketEntity>>
    fun getSocketConnectionStatus(): Flow<Boolean>
    fun startMarketUpdates()
    fun stopMarketUpdates()
    fun observeAndSaveRemoteMarketData(): Flow<Unit>
}