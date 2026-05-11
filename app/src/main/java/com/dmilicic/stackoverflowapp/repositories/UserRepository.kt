package com.dmilicic.stackoverflowapp.repositories

import com.dmilicic.stackoverflowapp.api.ApiService
import com.dmilicic.stackoverflowapp.models.UserModel
import com.dmilicic.stackoverflowapp.prefs.StackSharedPrefs
import javax.inject.Inject

class UserRepository @Inject constructor(
    val apiService: ApiService,
    val stackSharedPrefs: StackSharedPrefs
) {

     suspend fun getTopUsers(): List<UserModel> {
         return try {
             val response = apiService.getTopUsers(page = 1, pageSize = 20)
             response.items.ifEmpty {
                 emptyList()
             }
         } catch (e: Exception) {
             // log this exception in a real app
             emptyList()
         }
     }

    fun followUser(userId: Int) {
        // In a real app, this would make an API call to follow the user
        stackSharedPrefs.saveFollowedUser(userId, true)
    }

    fun unfollowUser(userId: Int) {
        stackSharedPrefs.saveFollowedUser(userId, false)
    }

    /**
     * Determines which users are followed from the given list.
     */
    fun getFollowedUsers(users: List<UserModel>): List<Int> {
        return users.filter { stackSharedPrefs.isFollowingUser(it.userId) }
            .map { it.userId }
    }

    fun isUserFollowed(userId: Int): Boolean {
        return stackSharedPrefs.isFollowingUser(userId)
    }
}