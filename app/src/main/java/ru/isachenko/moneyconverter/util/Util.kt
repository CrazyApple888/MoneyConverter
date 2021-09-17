package ru.isachenko.moneyconverter.util

import org.json.JSONObject
import ru.isachenko.moneyconverter.model.Wallet

object Util {

    fun parseJSON(
        currenciesJson: JSONObject,
        preferredCurrencies: List<String>
    ): List<Wallet> {
        val preferred = mutableListOf<Wallet>()
        //Codes of currencies
        val keys = currenciesJson.getJSONObject("Valute").keys()
        //JSONObjects of currencies
        val valutes = currenciesJson.getJSONObject("Valute")
        val mutableData = mutableListOf<Wallet>()
        for (i in 0 until valutes.length()) {
            val charCode = keys.next()
            val currentCurrency = valutes.getJSONObject(charCode)
            if (preferredCurrencies.contains(charCode)) {
                preferred.add(
                    Wallet(
                        charCode,
                        currentCurrency.getString("Name"),
                        currentCurrency.getDouble("Value"),
                        currentCurrency.getDouble("Previous")
                    )
                )
                continue
            }
            mutableData.add(
                Wallet(
                    charCode,
                    currentCurrency.getString("Name"),
                    currentCurrency.getDouble("Value"),
                    currentCurrency.getDouble("Previous")
                )
            )
        }
        preferred.addAll(mutableData)
        return preferred.toList()
    }
}