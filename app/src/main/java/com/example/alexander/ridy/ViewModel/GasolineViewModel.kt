package com.example.alexander.ridy.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.GasolineType
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.Model.network.Endpoint
import com.example.alexander.ridy.Model.network.InjectedViewModel
import com.example.alexander.ridy.Model.persistence.App
import com.example.alexander.ridy.Model.persistence.Ride.RideRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Class that represents a model that can have information of a Gasoline Object
 * @property endpoint the inferface that has methods to make calls for information outside of the application
 * @property rideRepository an abstraction layer that connects to the DAO for retrieving data out of the local database
 * @property allGasolines a list of all the gasolines from the local database
 * @property subscription a subscription subcribes itself to a method, it than gets a notifiction in case something changes to the method(ex. returns something)
 */
class GasolineViewModel : InjectedViewModel() {

    @Inject
    lateinit var endpoint: Endpoint

    @Inject
    lateinit var rideRepository: RideRepository

    init {
        App.component.inject(this)
    }

    val allGasolines : LiveData<List<Gasoline>> = rideRepository.gasolines

    private var currentGasoline = MutableLiveData<Gasoline>()

    private var subscription: Disposable? = null

    /**
     * Function that retrieves data from the internet by using the endpoint interface
     * @param gasolineType the type of gasoline
     */
    fun getGasoline(gasolineType: GasolineType) {
        //nog optimaal schrijven
        when(gasolineType) {
            GasolineType.Diesel -> subscription = endpoint.getDiesel()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveGasolineStart() }
                .doOnTerminate { onRetrieveGasolineFinish() }
                .subscribe(
                    { result -> onRetrieveGasolineSuccess(result) },
                    { error -> onRetrieveGasolineError(error) }
                )
            GasolineType.Super95 -> subscription = endpoint.getSuper95()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveGasolineStart() }
                .doOnTerminate { onRetrieveGasolineFinish() }
                .subscribe(
                    { result -> onRetrieveGasolineSuccess(result) },
                    { error -> onRetrieveGasolineError(error) }
                )
            GasolineType.LPG -> subscription = endpoint.getLPG()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveGasolineStart() }
                .doOnTerminate { onRetrieveGasolineFinish() }
                .subscribe(
                    { result -> onRetrieveGasolineSuccess(result) },
                    { error -> onRetrieveGasolineError(error) }
                )
        }
    }

    /**
     * Function which notifies the user that the application wasn't able to retrieve the gasoline
     * @param error the error why the function can't retrieve it
     */
    private fun onRetrieveGasolineError(error: Throwable) {
        Log.d("Retrieving gas ERROR", error.toString())
    }

    /**
     * Function which notifies the user that the application was able to retrieve the gasoline
     * @param result the gasoline object
     */
    private fun onRetrieveGasolineSuccess(result: Gasoline) {
        Log.d("Retrieving gas", "succes")
        currentGasoline.value = result
    }

    /**
     * Function which notifies the user that the application finish the retrieval of the gasoline
     */
    private fun onRetrieveGasolineFinish() {
        Log.d("Retrieving gas", "finish")
    }

    /**
     * Function which notifies the user that the application start the retrieval of the gasoline
     */
    private fun onRetrieveGasolineStart() {
        Log.d("Retrieving gas", "start")
    }

    /**
     * Function that inserts a gasoline into the local database
     * @param gasoline represents a Gasoline object
     */
    fun insert(gasoline: Gasoline) {
        rideRepository.insert(gasoline)
    }

    /**
     * Function that updates a [price] from a certain [gasolineType] in the database
     * @param price price of the gasoline
     * @param gasolineType type of the gasoline
     * @return returns a Livedata List from the gasolines, LiveData is used because it can monitor changes
     */
    fun updatePriceGasoline(prijs: Double, gasolineType: GasolineType) {
        rideRepository.updatePriceGasoline(prijs, gasolineType)
    }

    /**
     * Function that deletes all gasolines from the local database
     */
    fun deleteAllGasolines() {
        rideRepository.deleteAllGasolines()
    }


    /**
     * Disposes the subscription when the [InjectedViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    /**
     * Function to get the current gasoline object
     * @return returns an the gasoline in mutableLiveDataForm
     */
    fun getCurrentGasoline(): MutableLiveData<Gasoline> {
        return currentGasoline
    }

}