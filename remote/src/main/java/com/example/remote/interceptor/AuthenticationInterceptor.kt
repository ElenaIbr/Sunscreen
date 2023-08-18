package com.example.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("x-access-token", "openuv-24jv0rlkliur1r-io")
        requestBuilder.addHeader("Content-Type", "application/json")
        return chain.proceed(requestBuilder.build())
    }
}
