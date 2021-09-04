package ru.isachenko.moneyconverter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

object InfoGetter {
    private val url = "https://www.cbr-xml-daily.ru/daily_json.js"
    private lateinit var requestQueue: RequestQueue

    @SuppressLint("SetTextI18n")
    fun downloadData(@NonNull ctx: Context) {
        requestQueue = Volley.newRequestQueue(ctx)
        (ctx as Activity).findViewById<TextView>(R.id.text_view).text = "Processing"
        val request = StringRequest(Request.Method.GET, url,
            { response ->
                ctx.findViewById<TextView>(R.id.text_view).text = response
            },
            { ctx.findViewById<TextView>(R.id.text_view).text = "Fail" })
        requestQueue.add(request)
    }

    fun getInfo(): List<Wallet> {
        TODO("Not yet implemented")

        //TODO import volley on gradle
        // create request
        // add internet permission


    }
}