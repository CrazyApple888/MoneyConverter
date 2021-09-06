package ru.isachenko.moneyconverter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WalletAdapter(private val ctx: Context) :
    RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    private var currencies = emptyList<Wallet>()

    init {
        CurrenciesDownloader.asyncGet(ctx, updater = {
            currencies = it
            notifyDataSetChanged()
        })
    }

    override fun getItemCount() = currencies.size

    class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charCode: TextView = view.findViewById(R.id.char_code)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return WalletViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = currencies[position]
        holder.charCode.text = item.charCode
        holder.value.text = String.format(ctx.getString(R.string.currency_value_RUB), item.value.toString())
        Log.i("HOLDER", holder.value.text.toString())
    }
}