package com.msit.tmdb.core.data.remote

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status_code") var code: Int,
    @SerializedName("status_message")var message: String)