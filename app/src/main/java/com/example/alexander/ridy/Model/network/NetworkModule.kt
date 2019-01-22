package com.example.alexander.ridy.Model.network

import android.content.Context
import com.example.alexander.ridy.Model.persistence.Ride.RideDatabase
import com.example.alexander.ridy.Model.persistence.Ride.RideRepository
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory.*

/**
 * Module which provides all required dependencies for the network
 * @property BASE_URL the base url of the website which needs an additional string to make the calls
 * @property application the context of the application
 * @property rideDatabase the database which has the rides
 * @constructor Constructor to retrieve the database set the application property
 */
@Module
class NetworkModule {

    private val BASE_URL = "https://gist.githubusercontent.com"
    var application : Context
    var rideDatabase : RideDatabase

    constructor(application: Context) {
        this.application = application
        rideDatabase = RideDatabase.getInstance(application)
    }

    /**
     * Provides the endpoint service implementation
     * @param retrofit the retrofit object used to instantiate the service
     */
    @Provides
    internal fun provideEndpoint(retrofit: Retrofit): Endpoint {
        return retrofit.create(Endpoint::class.java)
    }

    /**
     * Provides the rideDatabase
     * @return returns the rideDatabase
     */
    @Provides
    internal fun provideDatabase() : RideDatabase {
        return rideDatabase
    }

    /**
     * Provides the applicationContext
     * @return returns the applicationContext
     */
    @Provides
    internal fun provideContext() : Context {
        return application
    }

    /**
     * Provides the riderepository
     * @return returns the rideRepository
     */
    @Provides
    internal fun provideRepository(): RideRepository {
        return RideRepository(rideDatabase.rideDAO())
    }

    /**
     * Return the Retrofit object.
     */
    @Provides
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
            return  retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
    }

    /**
     * Returns the OkHttpClient
     */
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()
    }

}