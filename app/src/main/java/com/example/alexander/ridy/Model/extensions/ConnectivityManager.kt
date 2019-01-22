package com.example.alexander.ridy.Model.extensions

import android.os.AsyncTask
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Class that checks if there is connection to the internet/4g
 * @param mConsumer the object that asks for the internet connection
 */
class ConnectivityManager(private val mConsumer: Consumer) : AsyncTask<Void, Void, Boolean>() {

    /**
     * Interface which represents the object that asks for the internet connection
     */
    interface Consumer {
        /**
         * Function which will eventually determine if there is internet or not
         * @param internet variable to decide
         */
        fun accept(internet: Boolean?)
    }

    init {
        execute()
    }

    /**
     * Function which tries to connect to a certain host to check if there is internet/4g
     * @return returns if it's possible or not
     */
    override fun doInBackground(vararg voids: Void): Boolean? {
        try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            return true
        } catch (e: IOException) {
            return false
        }

    }

    /**
     * Function which uses the accept function on the object consumer to make the decision
     */
    override fun onPostExecute(internet: Boolean?) {
        mConsumer.accept(internet)
    }
}