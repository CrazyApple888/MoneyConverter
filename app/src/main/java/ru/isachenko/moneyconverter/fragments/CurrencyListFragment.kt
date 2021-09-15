package ru.isachenko.moneyconverter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.adapter.WalletListAdapter
import ru.isachenko.moneyconverter.database.WalletViewModel
import ru.isachenko.moneyconverter.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        configureAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_data -> {
                viewModel.reloadData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureAdapter() {
        val adapter = WalletListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.getListWalletLiveData().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        viewModel.getData()
    }
}