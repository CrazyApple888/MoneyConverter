package ru.isachenko.moneyconverter

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.databinding.FragmentConverterBinding
import ru.isachenko.moneyconverter.datasource.CurrenciesSource

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private var currencies = emptyList<Wallet>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CurrenciesSource.asyncGet(updater = {
            currencies = it
        }, {
            Toast.makeText(context as Activity, "Can't update data", Toast.LENGTH_LONG).show()
        },
            this.requireContext()
        )
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = currencies.map { it.charCode }
        adapter = ArrayAdapter(requireContext(), R.layout.dropdown_converter_item, items)
        binding.dropdownConvertTo.setAdapter(adapter)

        binding.convertButton.setOnClickListener { convert() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_data -> {
                CurrenciesSource.asyncGet(updater = { list ->
                    currencies = list
                    val items = currencies.map { it.charCode }
                    adapter.addAll(items)
                    Toast.makeText(requireContext(), "Data has been updated!", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(requireContext(), "Can't update data", Toast.LENGTH_SHORT).show()
                },
                    requireContext()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun convert() {
        val valueFrom = binding.convertFromEditText.text.toString().toDoubleOrNull()
        if (null == valueFrom) {
            setResult(0.0)
            return
        }
        val currencyTo = binding.dropdownConvertTo.editableText.toString()
        val currencyValueTo = currencies.find { it.charCode == currencyTo }?.value
        if (null != currencyValueTo) {
            val resultValue = valueFrom / currencyValueTo
            setResult(resultValue)
        }
    }

    private fun setResult(resultValue: Double) {
        binding.convertionResultEditText.setText(
            String.format(
                getString(R.string.result_template),
                resultValue
            )
        )
    }
}