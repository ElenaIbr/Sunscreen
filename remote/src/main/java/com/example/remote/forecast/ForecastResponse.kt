package com.example.remote.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("result")
    @Expose
    var result: List<UiIndexHour>? = null,
) {
    data class UiIndexHour(
        @SerializedName("uv")
        @Expose
        var uv: Double? = null,
        @SerializedName("uv_time")
        @Expose
        var uvTime: String? = null
    )
}
