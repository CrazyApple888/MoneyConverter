package ru.isachenko.moneyconverter

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//TODO rewrite json downloading
class CurrenciesSource(private val context: Context) {
    private val URL = context.getString(R.string.source_url)
    private var currencies: List<Wallet> = emptyList()
    private val preferredCurrencies: List<String> =
        context.resources.getStringArray(R.array.preferred_currencies).asList()

    private fun parseJSON(currenciesJson: JSONObject) {
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
        errorListener: Response.ErrorListener
    ) {
        //TODO
        if (currencies.isNotEmpty()) {
            updater.invoke(currencies)
            return
        }
        val request = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                parseJSON(response)
                updater.invoke(currencies.toList())
                Log.i("CODES", codes().toString())
            },
            {
                errorListener.onErrorResponse(it)
            })
        Volley.newRequestQueue(context).add(request)
    }

    fun codes() = currencies.map { it.charCode }

    fun currency(code: String) = currencies.find { it.charCode == code }?.value
}
