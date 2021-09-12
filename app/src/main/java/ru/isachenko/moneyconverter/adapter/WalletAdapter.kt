package ru.isachenko.moneyconverter.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.isachenko.moneyconverter.datasource.CurrenciesSource
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet

@SuppressLint("NotifyDataSetChanged")
class WalletAdapter(context: Context) :
    RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    private var currencies = emptyList<Wallet>()

    init {
        CurrenciesSource.asyncGet(updater = {
            currencies = it
            notifyDataSetChanged()
        }, {
            Toast.makeText(context as Activity, "Can't update data", Toast.LENGTH_SHORT).show()
        },
            context
        )
    }

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
}