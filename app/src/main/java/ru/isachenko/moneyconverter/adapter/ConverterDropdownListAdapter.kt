package ru.isachenko.moneyconverter.adapter

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

class ConverterDropdownListAdapter(
    context: Context,
    @LayoutRes resource: Int,
    private var data: List<String>
) :
    ArrayAdapter<String>(context, resource, data) {

    override fun getCount() = data.size

    override fun getItem(position: Int) = data[position]

    fun setData(newData: List<String>) {
        data = newData
        notifyDataSetChanged()
    }

}