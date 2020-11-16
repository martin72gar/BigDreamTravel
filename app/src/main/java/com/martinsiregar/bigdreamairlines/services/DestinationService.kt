package com.martinsiregar.bigdreamairlines.services

import com.martinsiregar.bigdreamairlines.models.Destination
import retrofit2.Call
import retrofit2.http.GET

interface DestinationService {

    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>
}