package com.dmilicic.stackoverflowapp.repositories

import android.util.Log
import com.dmilicic.stackoverflowapp.api.ApiService
import com.dmilicic.stackoverflowapp.models.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(val apiService: ApiService) {

     suspend fun getTopUsers(): List<UserModel> {
         return try {
             val response = apiService.getTopUsers(page = 1, pageSize = 20)
             response.items.ifEmpty {
                 Log.d("UserRepository", "No users found")
                 emptyList()
             }
         } catch (e: Exception) {
             Log.d("UserRepository", "Error fetching top users", e)
             emptyList()
         }
     }
}