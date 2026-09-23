package di

import android.content.Context
import androidx.room.Room
import com.example.sockettest.BuildConfig
import data.local.AppDatabase
import data.local.MarketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.Polling
import io.socket.engineio.client.transports.WebSocket
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSocket(): Socket {
        val options = IO.Options().apply {
            path = BuildConfig.SOCKET_PATH
            forceNew = true
            reconnection = true
            transports = arrayOf(Polling.NAME, WebSocket.NAME)
        }
        return IO.socket(BuildConfig.BASE_URL, options)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "market_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMarketDao(db: AppDatabase): MarketDao {
        return db.marketDao()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}