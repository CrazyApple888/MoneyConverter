package ru.isachenko.moneyconverter

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ru.isachenko.moneyconverter.databinding.FragmentConverterBinding
import javax.sql.CommonDataSource

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private lateinit var dataSource: CurrenciesSource

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
        dataSource = CurrenciesSource(this.requireContext())
        val items = dataSource.codes()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_converter_item, items)
        binding.dropdownConvertTo.setAdapter(adapter)

        binding.convertButton.setOnClickListener { convert() }
    }

    private fun convert() {
        val valueFrom = binding.convertFromEditText.text.toString().toDoubleOrNull()
        if (null == valueFrom) {
            setResult(0.0)
            return
        }
        val currencyTo = binding.dropdownConvertTo.editableText.toString()
        val currencyValueTo = dataSource.currency(currencyTo)
        if (null != currencyValueTo) {
            //TODO limit symbols after '.' with format string
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