package ru.isachenko.moneyconverter.database

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.util.Util

object RemoteSource {

    private var queue: RequestQueue? = null

    fun asyncGet(
        context: Application,
        data: MutableLiveData<List<Wallet>>,
        errorListener: Response.ErrorListener,
        successToast: Toast
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
                successToast.show()
                Log.i("ISACHTAG", "GOT DATA FROM JSON")
            },
            {
                errorListener.onErrorResponse(it)
            })
        request.setShouldCache(false)
        if (null == queue) {
            queue = Volley.newRequestQueue(context)
        }
        queue!!.add(request)
    }
}
