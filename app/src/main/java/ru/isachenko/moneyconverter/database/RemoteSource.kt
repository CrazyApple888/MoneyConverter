package ru.isachenko.moneyconverter.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.util.Util

object RemoteSource {

    fun asyncGet(
        context: Context,
        data: MutableLiveData<List<Wallet>>,
        errorListener: Response.ErrorListener
    ) {
        val url = context.getString(R.string.source_url)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val preferredCurrencies: List<String> =
                    context.resources.getStringArray(R.array.preferred_currencies).asList()
                data.postValue(Util.parseJSON(response, preferredCurrencies))
            },
            {
                errorListener.onErrorResponse(it)
            })
        Volley.newRequestQueue(context).add(request)
    }
}
