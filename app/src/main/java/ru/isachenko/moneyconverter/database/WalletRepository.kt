package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import ru.isachenko.moneyconverter.model.Wallet

class WalletRepository(private val walletDao: WalletDao) {

    fun getData(
        appContext: Application,
        data: MutableLiveData<List<Wallet>>,
        errorListener: Response.ErrorListener
    ) {
        if (0 != walletDao.getWalletCount().or(0)) {
            data.postValue(walletDao.getAll())
            Log.i("ISACHTAG", "GOT DATA FROM REPO")
            return
        }
        RemoteSource.asyncGet(appContext, data, errorListener)
        Log.i("ISACHTAG", "GOT DATA FROM JSON")
    }

    fun reloadData(
        appContext: Application,
        data: MutableLiveData<List<Wallet>>,
        errorListener: Response.ErrorListener
    ) {
        RemoteSource.asyncGet(appContext, data, errorListener)
    }

    fun insertAll(vararg wallet: Wallet) {
        walletDao.insertAll(*wallet)
    }
}