package com.example.pixabay.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    fun getImages(

        @Query("key") key: String = "42455459-147eb8da95e8a85d494ed45c9",

        @Query("q") query: String,

        @Query("page") page: Int = 1,

        @Query("per_page") perPage: Int = 3

    ): Call<PixabayModel>
}