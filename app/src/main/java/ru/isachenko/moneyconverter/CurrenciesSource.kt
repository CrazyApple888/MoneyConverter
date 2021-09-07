package ru.isachenko.moneyconverter

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//Singleton class for getting currencies
object CurrenciesSource {

    private const val URL = "https://www.cbr-xml-daily.ru/daily_json.js"
    private var currencies: List<Wallet> = emptyList()

    private fun parseJSON(currenciesJson: JSONObject) {
        //Codes of currencies
        val keys = currenciesJson.getJSONObject("Valute").keys()
        //JSONObjects of currencies
        val valutes = currenciesJson.getJSONObject("Valute")
        val mutableData = mutableListOf<Wallet>()
        for (i in 0 until valutes.length()) {
            val charCode = keys.next()
            val currentCurrency = valutes.getJSONObject(charCode)
            mutableData.add(
                Wallet(
                    charCode,
                    currentCurrency.getString("Name"),
                    currentCurrency.getDouble("Value"),
                    currentCurrency.getDouble("Previous")
                )
            )
        }
        currencies = mutableData.toList()
    }

    //TODO
    // Check is saved data still actual
    fun asyncGet(
        ctx: Context,
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
        Volley.newRequestQueue(ctx).add(request)
    }

    fun codes(): List<String> = currencies.map { it.charCode }

    fun currency(code: String) = currencies.find { it.charCode == code }?.value
}
