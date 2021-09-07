package ru.isachenko.moneyconverter

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ru.isachenko.moneyconverter.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = CurrenciesSource.codes()
        //Log.i("ITEMS", items.toString())
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_converter_item, items)
        binding.dropdownConvertFrom.setAdapter(adapter)
        binding.dropdownConvertTo.setAdapter(adapter)

        binding.convertButton.setOnClickListener { convert() }
    }

    private fun convert() {
        val from = binding.convertFromEditText.text.toString().toDoubleOrNull()
        if (null == from) {
            setResult(0.0)
            return
        }
        val currencyFrom = binding.dropdownConvertFrom.editableText.toString()
        val currencyTo = binding.dropdownConvertTo.editableText.toString()
        val currencyValueFrom = CurrenciesSource.currency(currencyFrom)
        val currencyValueTo = CurrenciesSource.currency(currencyTo)
        if (null != currencyValueTo && null != currencyValueFrom) {
            val resultValue = from * currencyValueFrom / currencyValueTo
            setResult(resultValue)
        }
    }

    private fun setResult(resultValue: Double) {
        binding.convertionResultEditText.setText(resultValue.toString())
    }
}