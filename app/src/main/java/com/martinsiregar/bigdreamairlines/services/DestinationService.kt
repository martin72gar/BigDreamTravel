package com.martinsiregar.bigdreamairlines.services

import com.martinsiregar.bigdreamairlines.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id")id: Int): Call<Destination>
}