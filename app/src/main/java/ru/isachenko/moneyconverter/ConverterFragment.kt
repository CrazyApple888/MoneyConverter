package ru.isachenko.moneyconverter

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.isachenko.moneyconverter.adapter.ConverterDropdownListAdapter
import ru.isachenko.moneyconverter.database.WalletViewModel
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private var currencies = emptyList<Wallet>()
    private lateinit var adapter: ConverterDropdownListAdapter//ArrayAdapter<String>
    private lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        var items = emptyList<String>()//currencies.map { it.charCode }
        adapter = ConverterDropdownListAdapter(requireContext(), R.layout.dropdown_converter_item, items)
        binding.dropdownConvertTo.setAdapter(adapter)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewModel.getListWalletLiveData().observe(viewLifecycleOwner) { list ->
            currencies = list
            items = list.map { it.charCode }
            adapter.setData(items)
        }
        viewModel.getData()

        binding.convertButton.setOnClickListener { convert() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_data -> {
                /*CurrenciesSource.asyncGet(
                    this.requireContext(),
                    {
                        Toast.makeText(requireContext(), "Can't update data", Toast.LENGTH_SHORT)
                            .show()
                    },
                    { list ->
                        currencies = list
                        val items = currencies.map { it.charCode }
                        adapter.addAll(items)
                        Toast.makeText(
                            requireContext(),
                            "Data has been updated!",
                            Toast.LENGTH_SHORT
                        ).show()
                    })*/
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