package ru.isachenko.moneyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.isachenko.moneyconverter.viewmodel.WalletViewModel
import ru.isachenko.moneyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomMenuBar, navController)

    }

    override fun onStop() {
        val viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        val data = viewModel.data.value
        data?.toTypedArray()?.let { viewModel.insertAll(*it) }
        super.onStop()
    }
}