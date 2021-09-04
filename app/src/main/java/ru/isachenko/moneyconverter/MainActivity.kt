package ru.isachenko.moneyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import ru.isachenko.moneyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CurrencyGetter.downloadData(this)
        binding.bottomMenuBar.setOnItemSelectedListener { setOnItemSelectedListener(it) }
    }

    private fun setOnItemSelectedListener(item: MenuItem): Boolean {
        //TODO
        when (item.itemId) {
            R.id.list_bottom_button -> {
                binding.textView.text = CurrencyGetter.getSmth()
                return true
            }
            else -> {
                binding.textView.text = "ABOBA"
            }
        }
        return true
    }
}