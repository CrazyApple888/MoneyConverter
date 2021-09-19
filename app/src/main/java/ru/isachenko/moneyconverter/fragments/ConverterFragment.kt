package ru.isachenko.moneyconverter.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.adapter.ConverterDropdownListAdapter
import ru.isachenko.moneyconverter.viewmodel.WalletViewModel
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.databinding.FragmentConverterBinding
import ru.isachenko.moneyconverter.viewmodel.ConverterViewModel

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private var currencies = emptyList<Wallet>()
    private lateinit var adapter: ConverterDropdownListAdapter
    private lateinit var walletViewModel: WalletViewModel
    private lateinit var converterViewModel: ConverterViewModel
    private var inputMethodManager: InputMethodManager? = null

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
        var items = emptyList<String>()
        adapter = ConverterDropdownListAdapter(
            requireContext(),
            R.layout.dropdown_converter_item, items
        )
        binding.dropdownConvertTo.setAdapter(adapter)

        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        walletViewModel.data.observe(viewLifecycleOwner) { list ->
            currencies = list
            items = list.map { it.charCode }
            adapter.setData(items)
        }
        walletViewModel.getData()

        converterViewModel = ViewModelProvider(this).get(ConverterViewModel::class.java)
        setResult(converterViewModel.convertionResult)
        binding.convertButton.setOnClickListener { clickListener() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_data -> {
                walletViewModel.reloadData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clickListener() {
        if (null == inputMethodManager) {
            inputMethodManager =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        }
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()

        val valueFrom = binding.convertFromEditText.text.toString().toDoubleOrNull()
        val currencyValueTo =
            currencies.find { it.charCode == binding.dropdownConvertTo.editableText.toString() }?.value
        converterViewModel.convert(valueFrom, currencyValueTo)
        setResult(converterViewModel.convertionResult)
    }

    private fun setResult(resultValue: Double) {
        binding.convertToResult.text = String.format(
            getString(R.string.result_template),
            resultValue, binding.dropdownConvertTo.editableText.toString()
        )
    }
}