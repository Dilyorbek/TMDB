package com.msit.tmdb.core.data.remote

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class ApiResponse<T> {
    val TAG = ApiResponse::class.java.name
    val code: Int
    val body: T?
    val message: ApiError?

    val isSuccessful: Boolean
        get() = code in 200..300

    constructor(error: Throwable) {
        this.code = 500
        this.body = null
        this.message = ApiError(500, error.message ?: "")
    }

    constructor(response: Response<T>) {
        this.code = response.code()

        if (response.isSuccessful) {
            this.body = response.body()
            this.message = null
        } else {
            var errorMessage: ApiError? = null

            response.errorBody()?.let {
                try {
                    val gson = Gson()
                    errorMessage = gson.fromJson(it.string(), ApiError::class.java)
                    Log.e(TAG, "ResponseBody = ${errorMessage.toString()}")
                } catch (e: Exception) {
                    errorMessage = ApiError(code, response.message())
                    Log.e(TAG, response.message())
                }
            }

            Log.e(TAG, "Final error = ${errorMessage.toString()}")
            this.body = null
            this.message = errorMessage
        }
    }
}