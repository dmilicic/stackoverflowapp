package com.dmilicic.stackoverflowapp.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("badge_counts")
    val badgeCounts: BadgeCounts? = null,
    @SerializedName("account_id")
    val accountId: Int,
    @SerializedName("is_employee")
    val isEmployee: Boolean = false,
    @SerializedName("last_modified_date")
    val lastModifiedDate: Int = 0,
    @SerializedName("last_access_date")
    val lastAccessDate: Int = 0,
    @SerializedName("reputation_change_year")
    val reputationChangeYear: Int = 0,
    @SerializedName("reputation_change_quarter")
    val reputationChangeQuarter: Int = 0,
    @SerializedName("reputation_change_month")
    val reputationChangeMonth: Int = 0,
    @SerializedName("reputation_change_week")
    val reputationChangeWeek: Int = 0,
    @SerializedName("reputation_change_day")
    val reputationChangeDay: Int = 0,
    @SerializedName("reputation")
    val reputation: Int = 0,
    @SerializedName("creation_date")
    val creationDate: Int? = null,
    @SerializedName("user_type")
    val userType: String? = null,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("accept_rate")
    val acceptRate: Int? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("website_url")
    val websiteUrl: String? = null,
    @SerializedName("link")
    val link: String? = null,
    @SerializedName("profile_image")
    val profileImage: String? = null,
    @SerializedName("display_name")
    val displayName: String? = null
)

data class BadgeCounts(
    val bronze: Int,
    val silver: Int,
    val gold: Int
)