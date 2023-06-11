package com.example.domain.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(val successData: T?) : Resource<T>(successData)
    class Error<T>(val errorMessage: String, data: T? = null) : Resource<T>(data, errorMessage)
}
