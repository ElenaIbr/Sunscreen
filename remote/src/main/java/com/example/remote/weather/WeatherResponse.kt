package com.example.remote.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    @Expose
    var location: CurrentLocation? = null,
    @SerializedName("current")
    @Expose
    var current: CurrentWeather? = null,
    @SerializedName("forecast")
    @Expose
    var forecast: Forecast? = null
) {
    data class CurrentLocation(
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("region")
        @Expose
        var region: String? = null,
        @SerializedName("country")
        @Expose
        var country: String? = null,
        @SerializedName("lat")
        @Expose
        var lat: Double? = null,
        @SerializedName("lon")
        @Expose
        var lon: Double? = null,
        @SerializedName("tz_id")
        @Expose
        var tzId: String? = null,
        @SerializedName("localtime_epoch")
        @Expose
        var localtimeEpoch: Long? = null,
        @SerializedName("localtime")
        @Expose
        var localtime: String? = null
    )

    data class CurrentWeather(
        @SerializedName("wind_mph")
        @Expose
        var windMph: Double? = null,
        @SerializedName("wind_kph")
        @Expose
        var windKph: Double? = null,
        @SerializedName("wind_degree")
        @Expose
        var windDegree: Double? = null,
        @SerializedName("wind_dir")
        @Expose
        var windDir: String? = null,
        @SerializedName("cloud")
        @Expose
        var cloud: Double? = null,
        @SerializedName("uv")
        @Expose
        var uv: Double? = null,
        @SerializedName("temp_c")
        @Expose
        var temp: Double? = null
    )
    data class Forecast(
        @SerializedName("forecastday")
        @Expose
        var forecastDay: List<ForecastDay>? = null
    ) {
        data class ForecastDay(
            @SerializedName("date_epoch")
            @Expose
            var dateEpoch: Long? = null,
            @SerializedName("hour")
            @Expose
            var hour: List<Hour>? = null
        ) {
            data class Hour(
                @SerializedName("date")
                @Expose
                var date: String? = null,
                @SerializedName("time_epoch")
                @Expose
                var timeEpoch: Long? = null,
                @SerializedName("uv")
                @Expose
                var uv: Double? = null
            )
        }
    }
}
