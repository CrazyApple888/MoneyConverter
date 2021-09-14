package ru.isachenko.moneyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.isachenko.moneyconverter.model.Wallet

@Database(entities = [Wallet::class], version = 1)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    companion object {

        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getDatabase(context: Context): WalletDatabase {
            val tmp = INSTANCE
            if (null != tmp) {
                return tmp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDatabase::class.java,
                    "wallet_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}