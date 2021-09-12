package ru.isachenko.moneyconverter.database

import ru.isachenko.moneyconverter.model.Wallet

class WalletRepository(private val walletDao: WalletDao) {

    val readAllData = walletDao.readAll()

    suspend fun addAll(vararg wallet: Wallet) {
        walletDao.addAll(*wallet)
    }

    fun getSize() = walletDao.getWalletCount()
}