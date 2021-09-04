package ru.isachenko.moneyconverter


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//Singleton class for getting currencies
object CurrencyGetter {
    private const val url = "https://www.cbr-xml-daily.ru/daily_json.js"
    private var data: List<Wallet>? = null
    private lateinit var currenciesJson: JSONObject

    fun downloadData(ctx: Context) {
        //TODO
        // Check is saved data still actual

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response -> this.currenciesJson = response },
            {
                Toast.makeText(ctx as Activity, "Can't update data", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(ctx).add(request)
    }

    fun getCurrencies() {
        //TODO return currencies
        val keys = currenciesJson.getJSONObject("Valute").keys()
        val valutes = currenciesJson.getJSONObject("Valute")
        for (i in 0..valutes.length()) {
            //return valutes.getJSONObject(keys.next().toString())["CharCode"].toString()
        }

    }

    //TODO delete this
    fun getSmth() : String {
        val keys = currenciesJson.getJSONObject("Valute").keys()
        val valutes = currenciesJson.getJSONObject("Valute")
        for (i in 0..valutes.length()) {
            return valutes.getJSONObject(keys.next().toString()).getString("Name")
        }
        return "a"
    }

    fun getInfo(): List<Wallet> {
        TODO("Not yet implemented")

    }
}
