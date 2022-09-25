package com.example.speerassesment.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Constants {
    companion object {
        const val IS_FOLLOWING = "isFollowing"
        const val SELECTED_USER_NAME = "userName"

        fun isNetworkAvailable(context: Context) =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            }
    }
}