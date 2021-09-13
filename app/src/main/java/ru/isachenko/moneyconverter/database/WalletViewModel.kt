package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.isachenko.moneyconverter.model.Wallet

class WalletViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WalletRepository
    private val app = application
    var data: /*Mutable*/LiveData<List<Wallet>> //= MutableLiveData()

    init {
        val walletDao = WalletDatabase.getDatabase(application).walletDao()
        repository = WalletRepository(walletDao)
        data = repository.getData(application)
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            data = repository.getData(app)
            Log.i("ABOBA", "GOT DATA")
        }
    }

    fun addAll(vararg wallets: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAll(*wallets)
        }
    }
}