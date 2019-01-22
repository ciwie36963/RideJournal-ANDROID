package com.example.alexander.ridy.Model.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory.*

/**
 * Class that respresents a retrofitClientInstance
 * @property retrofit property that is of the type Retrofit, to makes calls
 * @property BASE_URL the base url of the website which needs an additional string to make the calls
 */
class RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://gist.githubusercontent.com"

    /**
     * Function that creates an instance of retrofit
     * @return returns retrofit instance
     */
    fun getRetrofitInstance(): Retrofit? {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(create())
                .build()
        return retrofit
    }
}