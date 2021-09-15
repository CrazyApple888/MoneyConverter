package ru.isachenko.moneyconverter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet

@SuppressLint("NotifyDataSetChanged")
class WalletListAdapter :
    RecyclerView.Adapter<WalletListAdapter.WalletViewHolder>() {

    private var currencies = emptyList<Wallet>()

    override fun getItemCount() = currencies.size

    class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charCode: TextView = view.findViewById(R.id.char_code)
        val value: TextView = view.findViewById(R.id.value)
        val name: TextView = view.findViewById(R.id.currency_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return WalletViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = currencies[position]
        holder.charCode.text = item.charCode
        holder.value.text = item.value.toString()
        holder.name.text = item.name
    }

    fun setData(newData: List<Wallet>) {
        currencies = newData
        notifyDataSetChanged()
    }
}