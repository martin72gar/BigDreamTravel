package com.martinsiregar.bigdreamairlines.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val URL = "http://192.168.8.102:9000/"

    //Create OKHttp Client
    private val okHttp = OkHttpClient.Builder()

    //Create Retrofit Builder
    private val builder:Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .client(okHttp.build())

    //Create Retrofit Instance
    private val retrofit : Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}