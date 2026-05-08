package com.dmilicic.stackoverflowapp.models

data class UserModel(
    val badgeCounts: BadgeCounts? = null,
    val accountId: Int,
    val isEmployee: Boolean = false,
    val lastModifiedDate: Int = 0,
    val lastAccessDate: Int = 0,
    val reputationChangeYear: Int = 0,
    val reputationChangeQuarter: Int = 0,
    val reputationChangeMonth: Int = 0,
    val reputationChangeWeek: Int = 0,
    val reputationChangeDay: Int = 0,
    val reputation: Int = 0,
    val creationDate: Int? = null,
    val userType: String? = null,
    val userId: Int,
    val acceptRate: Int? = null,
    val location: String? = null,
    val websiteUrl: String? = null,
    val link: String? = null,
    val profileImage: String? = null,
    val displayName: String? = null
)

data class BadgeCounts(
    val bronze: Int,
    val silver: Int,
    val gold: Int
)