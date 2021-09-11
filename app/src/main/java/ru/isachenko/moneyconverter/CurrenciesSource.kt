package ru.isachenko.moneyconverter

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object CurrenciesSource {
    private var currencies: List<Wallet> = emptyList()

    private fun parseJSON(currenciesJson: JSONObject, context: Context) {
        val preferredCurrencies: List<String> =
            context.resources.getStringArray(R.array.preferred_currencies).asList()
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
                        currentCurrency.getDouble("Value")
                    )
                )
                continue
            }
            mutableData.add(
                Wallet(
                    charCode,
                    currentCurrency.getString("Name"),
                    currentCurrency.getDouble("Value")
                )
            )
        }
        preferred.addAll(mutableData.shuffled())
        currencies = preferred.toList()
    }

    //TODO
    // Check is saved data still actual
    fun asyncGet(
        updater: (List<Wallet>) -> Unit,
        errorListener: Response.ErrorListener,
        context: Context
    ) {
        if (currencies.isNotEmpty()) {
            updater.invoke(currencies)
            return
        }
        val url = context.getString(R.string.source_url)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                parseJSON(response, context)
                updater.invoke(currencies)
            },
            {
                errorListener.onErrorResponse(it)
            })
        Volley.newRequestQueue(context).add(request)
    }
}
