package com.example.remote.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultAdapter(
    private val resultType: Type
): CallAdapter<Type, Call<ApiNetworkResult<Type>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<ApiNetworkResult<Type>> {
        return NetworkResultCall(call)
    }

}