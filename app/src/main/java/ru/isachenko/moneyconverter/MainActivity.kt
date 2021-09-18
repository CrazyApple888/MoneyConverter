package ru.isachenko.moneyconverter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.isachenko.moneyconverter.viewmodel.WalletViewModel
import ru.isachenko.moneyconverter.databinding.ActivityMainBinding
import ru.isachenko.moneyconverter.util.Util.LOG_TAG
import ru.isachenko.moneyconverter.util.Util.SHARED_PREFERENCES_NAME
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WalletViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomMenuBar, navController)

        preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        checkCurrencyActualityAndUpdate()
    }

    override fun onStop() {
        val data = viewModel.data.value
        data?.toTypedArray()?.let { viewModel.insertAll(*it) }
        super.onStop()
    }

    // Updates currencies if prev update was before current day
    // Once a day, because JSON updates per day
    @SuppressLint("SimpleDateFormat")
    private fun checkCurrencyActualityAndUpdate() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val today = Date()
        val dayMonth: String = dateFormat.format(today)
        val lastUpdated = preferences.getString("dateLastUpdated", "")

        if (dayMonth != lastUpdated) {
            viewModel.reloadData()
            val editor = preferences.edit()
            editor.putString("dateLastUpdated", dayMonth)
            editor.apply()
            Log.i(LOG_TAG, "PUT DATE TO SHARED PREFERENCES")
        }
    }
}