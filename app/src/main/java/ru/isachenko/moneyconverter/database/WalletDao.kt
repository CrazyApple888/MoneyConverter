package ru.isachenko.moneyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.isachenko.moneyconverter.model.Wallet

@Dao
interface WalletDao {
    @Insert(onConflict = REPLACE)
    fun insertAll(vararg wallets: Wallet)

    @Query("SELECT * FROM wallet")
    fun getAll(): List<Wallet>

    @Query("SELECT COUNT(charCode) FROM wallet")
    fun getWalletCount(): Int
}