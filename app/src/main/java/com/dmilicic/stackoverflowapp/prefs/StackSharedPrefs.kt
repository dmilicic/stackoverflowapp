package com.dmilicic.stackoverflowapp.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class StackSharedPrefs @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveFollowedUser(userId: Int, isFollowing: Boolean) {
        sharedPreferences.edit { putBoolean(userId.toString(), isFollowing) }
    }

    fun isFollowingUser(userId: Int): Boolean {
        return sharedPreferences.getBoolean(userId.toString(), false)
    }
}