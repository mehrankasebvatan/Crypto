package ir.kasebvatan.crypto.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.kasebvatan.crypto.data.local.CryptoDatabase
import ir.kasebvatan.crypto.data.local.dao.CoinDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinDao(database: CryptoDatabase): CoinDao {
        return database.coinDao()
    }
}