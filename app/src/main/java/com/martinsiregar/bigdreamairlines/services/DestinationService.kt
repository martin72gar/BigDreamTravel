package com.martinsiregar.bigdreamairlines.services

import com.martinsiregar.bigdreamairlines.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    @Headers("x-device-type: Android", "x-foo: bar")
    @GET("destination")
    fun getDestinationList(
        @QueryMap filter: HashMap<String, String>,
        @Header("Accept-Language") language: String
    ): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id")id: Int): Call<Destination>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id")id: Int,
        @Field("city") city: String,
        @Field("country") country: String,
        @Field("description") description: String
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id")id: Int): Call<Unit>
}