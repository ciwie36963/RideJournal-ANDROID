package com.example.alexander.ridy.Model.network

import com.example.alexander.ridy.Model.persistence.App
import com.example.alexander.ridy.ViewModel.GasolineViewModel
import com.example.alexander.ridy.ViewModel.RideViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing the inject functions for presenters.
 * Components act as the bridge between the Modules that know how to provide dependencies
 * and the actual objects that require something to be injected.
 *
 * More info can be found in [the documentation](https://google.github.io/dagger/api/2.14/dagger/Component.html)
 */
@Singleton
/**
 * All modules that are required to perform the injections into the listed objects should be listed
 * in this annotation
 */
@Component(modules = [NetworkModule::class])
interface ViewModelInjectorComponent {

    /**
     * Injects the dependencies into the specified gasolineViewModel.
     * @param gasolineViewModel the [GasolineViewModel] in which to inject the dependencies.
     */
    fun inject(gasolineViewModel: GasolineViewModel)

    /**
     * Injects the dependencies into the specified gasolineViewModel.
     * @param rideViewModel the [RideViewModel] in which to inject the dependencies.
     */
    fun inject(rideViewModel: RideViewModel)

    /**
     * Injects the dependencies into the specified gasolineViewModel.
     * @param application the [App] in which to inject the dependencies.
     */
    fun inject(application: App)

    /**
     * Interface needed to build the ViewModelInjectorComponent
     */
    @Component.Builder
    interface Builder {

        /**
         * Function to create the ViewModelInjectorComponent
         */
        fun build(): ViewModelInjectorComponent

        /**
         * Function that is also needed to create the ViewModelInjectorComponent
         */
        fun networkModule(networkModule : NetworkModule) : Builder
    }

}