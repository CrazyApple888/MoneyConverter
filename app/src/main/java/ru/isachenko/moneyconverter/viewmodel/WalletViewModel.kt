package ru.isachenko.moneyconverter.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.VolleyError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.database.WalletDatabase
import ru.isachenko.moneyconverter.database.WalletRepository
import ru.isachenko.moneyconverter.model.Wallet

class WalletViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WalletRepository
    private val app = application
    private val errorListener: (VolleyError) -> Unit = {
        val msg = app.getString(R.string.update_failure_message)
        Toast.makeText(
            app,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
    private val successMessage: Toast
    val data: LiveData<List<Wallet>> get() = repository.getData()

    init {
        val successMessageString = app.getString(R.string.update_successful_message)
        successMessage = Toast.makeText(app, successMessageString, Toast.LENGTH_SHORT)
        val walletDao = WalletDatabase.getDatabase(application).walletDao()
        repository = WalletRepository(walletDao)
        Log.i("ISACHTAG", "VM INIT")
    }


    fun getData() {
        val isNotEmpty = data.value?.isNotEmpty()
        if (isNotEmpty != null && isNotEmpty) {
            Log.i("ISACHTAG", "GOT SAVED DATA")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(app, errorListener, successMessage)
        }
    }

    fun reloadData() {
        repository.reloadData(app, errorListener, successMessage)
    }

    fun insertAll(vararg wallets: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAll(*wallets)
            Log.i("ISACHTAG", "DATA SAVED IN REPO")
        }
    }
}