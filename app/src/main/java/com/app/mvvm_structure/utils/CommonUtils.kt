package com.app.mvvm_structure.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.util.PatternsCompat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object CommonUtils {

    fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnected)
    }

    fun isValidNetworkURL(url: String): Boolean {
        return isValidUrl(url) && isValidNetworkProtocol(url)
    }

    fun isValidUrl(url: String): Boolean{
        val p: Pattern = PatternsCompat.WEB_URL
        val m: Matcher = p.matcher(url.toLowerCase(Locale.getDefault()))
        return m.matches()
    }

    fun isValidNetworkProtocol(url: String): Boolean {
        return (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url))
    }

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}