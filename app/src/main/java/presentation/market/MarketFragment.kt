package presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sockettest.R
import com.example.sockettest.databinding.FragmentMarketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarketViewModel by viewModels()
    private val marketAdapter by lazy { MarketAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = marketAdapter
        setHasFixedSize(true)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.marketItems.collectLatest { items ->
                        marketAdapter.submitList(items)
                    }
                }
                launch {
                    viewModel.isConnected.collectLatest { connected ->
                        updateConnectionStatusUI(connected)
                    }
                }
            }
        }
    }

    private fun updateConnectionStatusUI(isConnected: Boolean) = with(binding) {
        val context = root.context

        val statusColor = ContextCompat.getColor(
            context,
            if (isConnected) R.color.market_green else R.color.market_red
        )
        val statusTextRes = if (isConnected) R.string.status_connected else R.string.status_disconnected

        statusText.apply {
            text = getString(statusTextRes)
            setTextColor(statusColor)
        }

        headerDot.background?.mutate()?.setTint(statusColor)
        footerText.isVisible = !isConnected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
