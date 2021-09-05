package ru.isachenko.moneyconverter


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//Singleton class for getting currencies
object CurrenciesDownloader {

    private const val URL = "https://www.cbr-xml-daily.ru/daily_json.js"
    private var currencies: List<Wallet>? = null

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
    fun asyncGet(ctx: Context, updater: (List<Wallet>) -> Unit) {
        if (null != currencies) {
            updater.invoke(currencies!!)
            return
        }
        val request = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                parseJSON(response)
                updater.invoke(currencies!!)
            },
            {
                Toast.makeText(ctx as Activity, "Can't update data", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(ctx).add(request)
    }
}
