package ru.isachenko.moneyconverter.util

import org.json.JSONObject
import ru.isachenko.moneyconverter.model.Wallet

object Util {
    fun parseJSON(json: JSONObject) : List<Wallet>
    {
        //Codes of currencies
        val keys = json.getJSONObject("Valute").keys()
        //JSONObjects of currencies
        val valutes = json.getJSONObject("Valute")
        val mutableData = mutableListOf<Wallet>()
        for (i in 0 until valutes.length()) {
            val charCode = keys.next()
            val currentCurrency = valutes.getJSONObject(charCode)
            mutableData.add(
                Wallet(
                    charCode,
                    currentCurrency.getString("Name"),
                    currentCurrency.getDouble("Value")
                )
            )
        }
        return mutableData.toList()
    }
}