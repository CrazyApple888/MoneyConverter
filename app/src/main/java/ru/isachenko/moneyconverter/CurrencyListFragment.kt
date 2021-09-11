package ru.isachenko.moneyconverter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.isachenko.moneyconverter.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    //private var _binding: FragmentCurrencyListBinding? = null
    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var adapter: WalletAdapter

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
        adapter = WalletAdapter(this.requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_data -> {
                CurrenciesSource.asyncGet(updater = {
                    adapter.currencies = it
                    adapter.notifyDataSetChanged()
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
}