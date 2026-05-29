package ir.kasebvatan.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.kasebvatan.crypto.data.local.dao.CoinDao
import ir.kasebvatan.crypto.data.local.entity.CoinEntity

@Database(
    entities = [CoinEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}