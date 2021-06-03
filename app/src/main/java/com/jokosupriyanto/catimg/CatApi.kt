package com.jokosupriyanto.catimg

import retrofit2.Call
import retrofit2.http.GET

interface CatApi {

    @GET("rest")
    fun getMovies(): Call<Cat>
}