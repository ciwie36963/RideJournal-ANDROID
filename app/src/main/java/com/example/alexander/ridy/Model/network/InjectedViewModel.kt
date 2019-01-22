package com.example.alexander.ridy.Model.network

import android.arch.lifecycle.ViewModel
import com.example.alexander.ridy.Model.persistence.App
import com.example.alexander.ridy.ViewModel.GasolineViewModel
import com.example.alexander.ridy.ViewModel.RideViewModel

/**
 * Base class for all ViewModels that require injection through Dagger.
 */
abstract class InjectedViewModel : ViewModel() {

    /**
     * An ViewModelInjectorComponent is required to do the actual injecting.
     * Every Component has a default builder to which you can add all
     * modules that will be needed for the injection.
     */
    private val injector: ViewModelInjectorComponent = DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule(App.context))
            .build()

    /**
     * Perform the injection when the ViewModel is created
     */
    init {
        inject()
    }

    /**
     * Injects the required dependencies.
     * We need the 'when(this)' construct for each new ViewModel as the 'this' reference should
     * refer to an instance of that specific ViewModel.
     * Just injecting into a generic InjectedViewModel is not specific enough for Dagger.
     */
    private fun inject() {
        when (this) {
            is RideViewModel -> injector.inject(this)
            is GasolineViewModel -> injector.inject(this)
        }
    }

}