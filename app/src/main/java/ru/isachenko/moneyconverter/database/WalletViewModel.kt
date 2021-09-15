package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import android.widget.Toast
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

    companion object {
        private val data = MutableLiveData<List<Wallet>>()
    }

    init {
        val walletDao = WalletDatabase.getDatabase(application).walletDao()
        repository = WalletRepository(walletDao)
        Log.i("ISACHTAG", "VM INIT")
    }

    fun getListWalletLiveData(): LiveData<List<Wallet>> = data

    fun getData() {
        val isNotEmpty = data.value?.isNotEmpty()
        if (isNotEmpty != null && isNotEmpty) {
            Log.i("ISACHTAG", "GOT SAVED DATA")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData(app, data) {
                Toast.makeText(
                    app,
                    "Can't update data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun reloadData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reloadData(app, data) {
                Toast.makeText(
                    app,
                    "Can't update data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun insertAll(vararg wallets: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAll(*wallets)
            Log.i("ISACHTAG", "DATA SAVED")
        }
    }
}