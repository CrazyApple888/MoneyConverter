package ru.isachenko.moneyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.isachenko.moneyconverter.model.Wallet

@Database(entities = [Wallet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao() : WalletDao
}