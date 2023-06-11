package com.example.remote.base

sealed class ApiNetworkResult<T : Any> {
    class Success<T: Any>(val data: T?) : ApiNetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : ApiNetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : ApiNetworkResult<T>()
}
