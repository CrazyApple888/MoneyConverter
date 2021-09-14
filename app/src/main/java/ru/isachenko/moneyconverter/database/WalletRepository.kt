package ru.isachenko.moneyconverter.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.isachenko.moneyconverter.model.Wallet

class WalletRepository(private val walletDao: WalletDao) {

    private var data = MutableLiveData<List<Wallet>>()

    fun getData() : List<Wallet> {
        if (0 == walletDao.getWalletCount().or(0)) {
            return emptyList()
        }
        return walletDao.getAll()
    }

    fun insertAll(vararg wallet: Wallet) {
        walletDao.insertAll(*wallet)
    }

    fun getSize() = walletDao.getWalletCount()
}