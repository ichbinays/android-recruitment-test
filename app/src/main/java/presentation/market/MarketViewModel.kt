package presentation.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sockettest.R
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.MarketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    val marketItems: StateFlow<List<MarketUiState>> = marketRepository.getLocalMarketItems()
        .map { entities ->
            entities.map { entity ->
                val isPositive = entity.changePercent >= 0
                MarketUiState(
                    symbol = entity.symbol,
                    formattedPrice = String.format(Locale.US, "%.4f", entity.price),
                    formattedTimestamp = timeFormat.format(Date(entity.timestamp)),
                    formattedChange = String.format(Locale.US, if (isPositive) "+%.3f%%" else "%.3f%%", entity.changePercent),
                    changeColor = if (isPositive) R.color.market_green else R.color.market_red,
                    changeBackground = if (isPositive) R.drawable.bg_badge_green else R.drawable.bg_badge_red
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isConnected: StateFlow<Boolean> = marketRepository.getSocketConnectionStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        marketRepository.startMarketUpdates()

        viewModelScope.launch {
            isConnected.collectLatest { connected ->
                if (connected) {
                    marketRepository.observeAndSaveRemoteMarketData().collect {
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        marketRepository.stopMarketUpdates()
    }
}
