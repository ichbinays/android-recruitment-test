package data.repository

import data.local.MarketDao
import data.local.MarketEntity
import data.mapper.toEntity
import data.remote.MarketSocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRepositoryImpl @Inject constructor(
    private val socketService: MarketSocketService,
    private val marketDao: MarketDao
) : MarketRepository {

    override fun getLocalMarketItems(): Flow<List<MarketEntity>> {
        return marketDao.getAllMarketItems()
    }

    override fun getSocketConnectionStatus(): Flow<Boolean> {
        return socketService.observeConnectionStatus()
    }

    override fun startMarketUpdates() {
        socketService.connect()
    }

    override fun stopMarketUpdates() {
        socketService.disconnect()
    }

    override fun observeAndSaveRemoteMarketData(): Flow<Unit> {
        return socketService.observeMarketData()
            .map { jsonObject ->
                val symbol = jsonObject.optString("1").takeIf { it.isNotEmpty() && it != "null" }
                    ?: jsonObject.optString("symbol").takeIf { it.isNotEmpty() && it != "null" }
                    ?: "UNKNOWN"

                val existing = marketDao.getMarketItemBySymbol(symbol)
                jsonObject.toEntity(existing?.price)
            }
            .onEach { localEntity ->
                marketDao.insertMarketItems(listOf(localEntity))
            }
            .map { }
    }
}
