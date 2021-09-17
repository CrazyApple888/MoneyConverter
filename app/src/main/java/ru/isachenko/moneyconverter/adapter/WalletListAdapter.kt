package ru.isachenko.moneyconverter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet

@SuppressLint("NotifyDataSetChanged")
class WalletListAdapter(private val template: String) :
    RecyclerView.Adapter<WalletListAdapter.WalletViewHolder>() {

    private var currencies = emptyList<Wallet>()

    override fun getItemCount() = currencies.size

    class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charCode: TextView = view.findViewById(R.id.char_code)
        val value: TextView = view.findViewById(R.id.value)
        val name: TextView = view.findViewById(R.id.currency_name)
        val imageArrow: ImageView = view.findViewById(R.id.image_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return WalletViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = currencies[position]
        holder.charCode.text = item.charCode
        holder.value.text = String.format(template, item.value)
        holder.name.text = item.name
        holder.imageArrow.setImageResource(
            if (item.isNewValueGreater()) {
                R.drawable.ic_stonks
            } else {
                R.drawable.ic_not_stonks
            }
        )
    }

    fun setData(newData: List<Wallet>) {
        currencies = newData
        notifyDataSetChanged()
    }
}