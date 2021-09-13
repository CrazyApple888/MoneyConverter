package ru.isachenko.moneyconverter.remotedatasource

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ru.isachenko.moneyconverter.R
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.util.Util


object Downloader {

    val data: MutableLiveData<List<Wallet>> = MutableLiveData()

    fun getData(app: Application,
    errorListener: Response.ErrorListener) {
        val url = app.getString(R.string.source_url)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                data.value = Util.parseJSON(response)
            },
            {
                errorListener.onErrorResponse(it)
            })
        Volley.newRequestQueue(app).add(request)
    }
}