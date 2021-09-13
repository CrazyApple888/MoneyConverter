package ru.isachenko.moneyconverter.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.isachenko.moneyconverter.model.Wallet

@Dao
interface WalletDao {

    /*//TODO delete this
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg wallets: Wallet)

    @Query("SELECT * FROM wallet_table")
    fun getAll(): List<Wallet>

    @Update
    fun updateAll(vararg wallets: Wallet)
*/
    //---------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(vararg wallets: Wallet)

    @Query("SELECT * FROM wallet_table")
    fun readAll(): LiveData<List<Wallet>>

    @Query("SELECT COUNT(charCode) FROM wallet_table")
    fun getWalletCount(): Int
}