package com.android.movieoftheday.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val success: Boolean,
    @SerializedName("status_code")val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)
