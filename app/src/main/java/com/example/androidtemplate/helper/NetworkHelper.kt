package com.example.androidtemplate.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Created by widist on 24/02/18.
 */
object NetworkHelper {

    private const val NETWORK_NOT_CONNECTED = -1
    private const val NETWORK_CONNECTED = 1
    //this variable is equal with -> ConnectivityManager.EXTRA_INET_CONDITION
    private const val EXTRA_INET_CONDITION = "inetCondition"

    const val NETWORK_CHANGE_ACTION = "NETWORK_CHANGE_ACTION"
    const val NETWORK_CHANGE_KEY = "NETWORK_CHANGE_KEY"

    fun isConnected(context: Context): Boolean {
        val status = getConnectivityStatus(context)
        return status == NETWORK_CONNECTED
    }

    fun getConnectionManager(context: Context): ConnectivityManager? {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getNetworkInfo(connectivityManager: ConnectivityManager): NetworkInfo? {
        return connectivityManager.activeNetworkInfo
    }

    fun getConnectivityStatus(context: Context): Int {
        val cm = getConnectionManager(context)!!
        val activeNetwork = getNetworkInfo(cm)
        if (null != activeNetwork) {
            println("active network: ${activeNetwork!!.type}")
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return NETWORK_CONNECTED

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return NETWORK_CONNECTED
        }
        return NETWORK_NOT_CONNECTED
    }

    fun getNetworkChangeReceiver(context: Context, action: (Boolean) -> Unit): BroadcastReceiver {
        val networkChangeReceiver = NetworkStateReceiver(isConnected(context), action)
        return networkChangeReceiver
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getNetworkChangeCallback(context: Context, action: (Boolean)->Unit): ConnectivityManager.NetworkCallback{
        val callBack = NetworkCallBack(isConnected(context), action)
        return callBack
    }

    fun registerNetworkChange(context: Context, receiver: BroadcastReceiver){
        context.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun registerNetworkChange(context: Context, callBack: ConnectivityManager.NetworkCallback){
        val cm = getConnectionManager(context)
        val request = NetworkRequest.Builder().build()
        cm?.registerNetworkCallback(request, callBack)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallBack(connected: Boolean, action: (Boolean)->Unit): ConnectivityManager.NetworkCallback(){

        var isConnected: Boolean = false
        var action: ((Boolean)->Unit) = {}

        init {
            isConnected = connected
            this.action = action
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
            isConnected = false
            action.invoke(false)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            if (!isConnected){
                action.invoke(true)
                isConnected = true
            }
        }
    }

    class NetworkStateReceiver(connected: Boolean, action: (Boolean) -> Unit) : BroadcastReceiver() {
        var isConnected: Boolean = false
        var action: ((Boolean) -> Unit) = {}

        init {
            isConnected = connected
            this.action = action
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            context ?: return; intent ?: return
            var connected = false

            val cm = getConnectionManager(context)!!
            val activeNetwork = getNetworkInfo(cm)

            if (activeNetwork != null && activeNetwork.state == NetworkInfo.State.CONNECTED) {
                connected = true
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
                connected = false
            }

            if (connected) {
                if (!isConnected) {
                    action.invoke(true)
                    isConnected = true
                }
            } else {
                isConnected = false
                action.invoke(false)
            }
        }
    }
}
