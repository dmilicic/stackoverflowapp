package com.dmilicic.stackoverflowapp.api


import com.dmilicic.stackoverflowapp.models.ApiResponse
import com.dmilicic.stackoverflowapp.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    suspend fun getTopUsers(@Query("page") page: Int, @Query("pageSize") pageSize: Int): ApiResponse
}