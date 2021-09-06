package ru.isachenko.moneyconverter

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
        val items = "TODO".toCharArray().asList()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_converter_item, items)
        binding.autoCompleteTextView.setAdapter(adapter)
    }
}