package com.example.alexander.ridy.Model.network

import com.example.alexander.ridy.Model.domain.Gasoline
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import retrofit2.http.GET
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart
import kotlin.collections.ArrayList

/**
 * Interface which makes calls to the Database
 */
interface Endpoint {

    /**
     * Call to get Diesel information
     * @return an observable object, from Gasoline that can check if the call was OK
     */
    @GET("/ciwie36963/0e5a4f4b7abe56bc76e90cc8f3d0d71a/raw/5b3be571d5baaf91d888df03d80a4292f33436d3/gistfile1.txt")
    fun getDiesel(): Observable<Gasoline>

    /**
     * Call to get Super95 information
     * @return an observable object, from Gasoline that can check if the call was OK
     */
    @GET("/ciwie36963/06bb75be43e31dd79897844e767ade0d/raw/b799df74c9a40682af82aa75ffcd8bc056f53db3/gistfile1.txt")
    fun getSuper95(): Observable<Gasoline>

    /**
     * Call to get LPG information
     * @return an observable object, from Gasoline that can check if the call was OK
     */
    @GET("/ciwie36963/628a28915b42b704eddc0c0e1901febf/raw/ba656c7d0356a760f4e50c51bff0da0bd7a66557/gistfile1.txt")
    fun getLPG(): Observable<Gasoline>
}