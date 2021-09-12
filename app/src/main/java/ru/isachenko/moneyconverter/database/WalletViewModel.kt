package ru.isachenko.moneyconverter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.isachenko.moneyconverter.model.Wallet

class WalletViewModel(application: Application): AndroidViewModel(application) {

    private val repository: WalletRepository
    val readAllData: LiveData<List<Wallet>>

    init {
        val walletDao = WalletDatabase.getDatabase(application).walletDao()
        repository = WalletRepository(walletDao)
        readAllData = repository.readAllData
    }

    fun addAll(vararg wallets: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAll(*wallets)
        }
    }
}