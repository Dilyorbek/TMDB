package com.msit.tmdb.core.data.remote

import com.msit.tmdb.core.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest
            .url().newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

}