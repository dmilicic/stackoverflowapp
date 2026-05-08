package com.dmilicic.stackoverflowapp.api


import com.dmilicic.stackoverflowapp.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getTopUsers(@Query("page") page: Int): Call<List<UserModel>>
}