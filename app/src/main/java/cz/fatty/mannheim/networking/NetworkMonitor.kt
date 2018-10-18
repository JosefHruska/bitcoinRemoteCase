package cz.fatty.mannheim.networking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import cz.fatty.mannheim.App.Companion.app
import cz.fatty.mannheim.extensions.withInitialValue

object NetworkMonitor : BroadcastReceiver() {

    val isConnected = MutableLiveData<Boolean>().withInitialValue(true)

    val listeners = mutableListOf<ConnectionChangeListener>()

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = app().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isOnline = activeNetwork != null && activeNetwork.isConnected
        isConnected.postValue(isOnline)
        listeners.forEach {
            it.onConnectionChanged(isOnline)
        }
    }

    fun isConnected(): Boolean {
        val cm = app().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}

interface ConnectionChangeListener {

    fun onConnectionChanged(isConnected: Boolean)
}

class NoNetworkException(url: String) : RuntimeException(url)