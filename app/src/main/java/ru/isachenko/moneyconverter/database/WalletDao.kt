package ru.isachenko.moneyconverter.database

import androidx.room.*
import ru.isachenko.moneyconverter.model.Wallet

@Dao
interface WalletDao {
    @Insert
    fun insertAll(vararg wallets: Wallet)

    @Query("SELECT * FROM wallet")
    fun getAll(): List<Wallet>

    @Update
    fun updateAll(vararg wallets: Wallet)

    @Query("SELECT COUNT(charCode) FROM wallet")
    fun getWalletCount(): Int
}