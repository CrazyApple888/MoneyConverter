package ru.isachenko.moneyconverter


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//TODO refactor class
//Singleton class for getting currencies
object CurrencyGetter {
    private const val url = "https://www.cbr-xml-daily.ru/daily_json.js"
    private lateinit var currenciesJson: JSONObject
    private var _currencies: List<Wallet>? = null
    val currencies: List<Wallet>
        get() {
            if (_currencies != null) {
                return _currencies!!
            }
            //Codes of currencies
            val keys = currenciesJson.getJSONObject("Valute").keys()
            //JSONObjects of currencies
            val valutes = currenciesJson.getJSONObject("Valute")
            val mutableData = mutableListOf<Wallet>()
            for (i in 0 until valutes.length()) {
                val charCode = keys.next()
                val currentValute = valutes.getJSONObject(charCode)
                mutableData.add(
                    Wallet(
                        charCode,
                        currentValute.getString("Name"),
                        currentValute.getDouble("Value"),
                        currentValute.getDouble("Previous")
                    )
                )
            }
            _currencies = mutableData.toList()
            return _currencies!!
        }

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
}
