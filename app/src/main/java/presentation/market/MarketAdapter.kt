package presentation.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sockettest.databinding.ItemMarketBinding

class MarketAdapter : ListAdapter<MarketUiState, MarketAdapter.MarketViewHolder>(MarketDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MarketViewHolder(private val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MarketUiState) = binding.run {
            tvSymbol.text = item.symbol
            tvPrice.text = item.formattedPrice
            tvTimestamp.text = item.formattedTimestamp
            
            tvChange.apply {
                text = item.formattedChange
                setTextColor(ContextCompat.getColor(context, item.changeColor))
                setBackgroundResource(item.changeBackground)
            }
        }
    }

    class MarketDiffCallback : DiffUtil.ItemCallback<MarketUiState>() {
        override fun areItemsTheSame(oldItem: MarketUiState, newItem: MarketUiState): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: MarketUiState, newItem: MarketUiState): Boolean {
            return oldItem == newItem
        }
    }
}
