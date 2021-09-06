package ru.isachenko.moneyconverter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.isachenko.moneyconverter.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    //private var _binding: FragmentCurrencyListBinding? = null
    private lateinit var binding: FragmentCurrencyListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = WalletAdapter(this.requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }
}