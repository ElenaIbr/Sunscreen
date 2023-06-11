package com.example.remote.base

import retrofit2.HttpException
import retrofit2.Response

fun <T : Any> handleApiResult(
    execute: () -> Response<T>
): ApiNetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful) {
            ApiNetworkResult.Success(body)
        } else {
            ApiNetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        ApiNetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiNetworkResult.Exception(e)
    }
}
