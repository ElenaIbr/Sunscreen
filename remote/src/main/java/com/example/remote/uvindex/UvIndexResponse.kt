package com.example.remote.uvindex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UvIndexResponse(
    @SerializedName("result")
    @Expose
    var result: UvIndex? = null,
) {
    data class UvIndex(
        @SerializedName("uv")
        @Expose
        var uv: Double? = null
    )
}
