package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import ru.isachenko.moneyconverter.model.Wallet
import javax.inject.Singleton

@Singleton
class WalletRepository(private val walletDao: WalletDao) {

    companion object {
        private val data = MutableLiveData<List<Wallet>>()
    }

    fun getData() : LiveData<List<Wallet>> = data

    fun updateData(
        appContext: Application,
        errorListener: Response.ErrorListener,
        successMessage: Toast
    ) {
        if (0 != walletDao.getWalletCount().or(0)) {
            data.postValue(walletDao.getAll())
            Log.i("ISACHTAG", "GOT DATA FROM REPO")
            return
        }
        RemoteSource.asyncGet(appContext, data, errorListener, successMessage)
    }

    fun reloadData(
        appContext: Application,
        errorListener: Response.ErrorListener,
        successMessage: Toast
    ) {
        RemoteSource.asyncGet(appContext, data, errorListener, successMessage)
    }

    fun insertAll(vararg wallet: Wallet) {
        walletDao.insertAll(*wallet)
    }
}