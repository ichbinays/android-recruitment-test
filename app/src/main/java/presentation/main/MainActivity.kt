package presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sockettest.R
import com.example.sockettest.databinding.ActivityMainBinding
import presentation.market.MarketFragment

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MarketFragment())
                .commit()
    }
}
