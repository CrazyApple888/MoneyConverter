package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.remotedatasource.Downloader

class WalletRepository(private val walletDao: WalletDao) {

    private var data: LiveData<List<Wallet>>? = null

    fun getData(app: Application) : LiveData<List<Wallet>> {
        if (null != data) {
            Log.i("ABOBA", "NULL COMPARE")
            return data!!
        }
        if (0 != walletDao.getWalletCount().or(0)) {
            Log.i("ABOBA", "DAO GET")
            return walletDao.readAll()
        }
        //TODO make Toast here
        Downloader.getData(app) { }
        data = Downloader.data
        data?.value?.get(0)?.charCode?.let { Log.i("ABOBA", it) }
        return data!!
    }

    suspend fun addAll(vararg wallet: Wallet) {
        walletDao.addAll(*wallet)
    }

    fun getSize() = walletDao.getWalletCount()
}