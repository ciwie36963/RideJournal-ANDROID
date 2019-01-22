package com.example.alexander.ridy.Model.persistence

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.alexander.ridy.Model.network.DaggerViewModelInjectorComponent
import com.example.alexander.ridy.Model.network.NetworkModule
import com.example.alexander.ridy.Model.network.ViewModelInjectorComponent

/**
 * Class that gives the context object to the NetworkModule.
 * The created component is saved in a companion object so it can be used through the whole app
 * Has to be registered in the manifest file
 */
class App : Application() {

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     * @property component a var that represents the ViewModelInjectorComponent
     * @property context a var that represents the Context
     */
   companion object {
       lateinit var component : ViewModelInjectorComponent
       @SuppressLint("StaticFieldLeak")
       lateinit var context: Context
    }

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled,
     * the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate() {
        super.onCreate()
        context = this

       component = DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule(this))
            .build()
    }
}