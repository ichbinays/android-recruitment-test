package data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarketEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marketDao(): MarketDao
}