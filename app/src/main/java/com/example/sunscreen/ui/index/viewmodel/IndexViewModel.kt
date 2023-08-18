package com.example.sunscreen.ui.index.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FetchUvEntity
import com.example.domain.models.FetchForecastModel
import com.example.domain.models.ForecastModel
import com.example.domain.usecases.FetchForecastInBackgroundUseCase
import com.example.domain.usecases.FetchUvUseCase
import com.example.domain.usecases.GetDateAndDayOfWeekUseCase
import com.example.domain.usecases.GetForecastByDateUseCase
import com.example.domain.usecases.GetLocationInBackgroundUseCase
import com.example.domain.usecases.GetUserNameUseCase
import com.example.domain.usecases.GetUvValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val fetchUvUseCase: FetchUvUseCase,
    private val getUvValueUseCase: GetUvValueUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getDateAndDayOfWeekUseCase: GetDateAndDayOfWeekUseCase,
    private val fetchForecastInBackgroundUseCase: FetchForecastInBackgroundUseCase,
    private val getLocationInBackgroundUseCase: GetLocationInBackgroundUseCase,
    private val getForecastByDateUseCase: GetForecastByDateUseCase
) : ViewModel() {

    private val _indexState = MutableStateFlow(IndexState())
    val indexState: StateFlow<IndexState> = _indexState

    init {
        getLocation()
        getUvValue()
        getUserName()
        getDateAndDayOfWeekUseCase()
    }

    fun sendEvent(indexEvent: IndexEvent) {
        when(indexEvent) {
            is FetchForecast -> {
                fetchForecast()
            }
            is FetchUvValue -> {
                fetchUvValue()
            }
            is SetCoordinates -> {
                setCoordinates(indexEvent.latitude, indexEvent.longitude)
            }
        }
    }

    private fun getUvValue() {
        viewModelScope.launch {
            getUvValueUseCase.execute(Unit).collect { flow ->
                _indexState.value = _indexState.value.copy(
                    index = flow?.indexModel
                )
                getForecast()
            }
        }
    }
    private fun getForecast() {
        _indexState.value.index?.date?.let { date ->
            viewModelScope.launch {
                getForecastByDateUseCase.execute(date).collect { flow ->
                    _indexState.value = _indexState.value.copy(
                        forecast = flow,
                        solarActivityLevel = getForecastByCurrentTime(flow)
                    )
                }
            }
        }
    }
    private fun getUserName() {
        viewModelScope.launch {
            getUserNameUseCase.execute(Unit).collect { flow ->
                _indexState.value = _indexState.value.copy(
                    userName = flow
                )
            }
        }
    }
    private fun fetchForecast() {
        viewModelScope.launch {
            fetchForecastInBackgroundUseCase.execute(
                FetchForecastModel(
                    longitude = _indexState.value.longitude ?: 0.0,
                    latitude = _indexState.value.latitude ?: 0.0,
                    date = Instant.now().toString()
                )
            )
        }
    }
    private fun fetchUvValue() {
        viewModelScope.launch {
            fetchUvUseCase.execute(
                "${_indexState.value.latitude},${_indexState.value.longitude}"
            ).collect { flow ->
                when (flow) {
                    is FetchUvEntity.Success -> {
                        _indexState.value = _indexState.value.copy(
                            isLoading = false
                        )
                    }
                    is FetchUvEntity.Loading -> {
                        _indexState.value = _indexState.value.copy(
                            isLoading = true
                        )
                    }
                    is FetchUvEntity.Failure -> {
                        _indexState.value = _indexState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
    private fun setCoordinates(latitude: Double, longitude: Double) {
        if (
            _indexState.value.latitude?.toInt() != latitude.toInt() ||
            _indexState.value.longitude?.toInt() != longitude.toInt()
        ) {
            _indexState.value = _indexState.value.copy(
                latitude = latitude,
                longitude = longitude
            )
        }
    }
    private fun getLocation() {
        viewModelScope.launch {
            getLocationInBackgroundUseCase.execute(Unit)
        }
    }
    private fun getDateAndDayOfWeekUseCase() {
        viewModelScope.launch {
            getDateAndDayOfWeekUseCase.execute(Unit).collect { flow ->
                _indexState.value = _indexState.value.copy(
                    date = flow
                )
            }
        }
    }
}

private fun getSolarActivityLevel(uvValue: Double): SolarActivity {
    return when (uvValue) {
        in 0.0..2.0 -> SolarActivity.Low
        in 3.0..5.0 -> SolarActivity.Medium
        in 6.0..7.0 -> SolarActivity.High
        in 8.0..10.00 -> SolarActivity.VeryHigh
        else -> SolarActivity.VeryHigh
    }
}

enum class SolarActivity {
    Low,
    Medium,
    High,
    VeryHigh
}

fun getForecastByCurrentTime(forecast: List<ForecastModel.Hour>?): SolarActivity {
    val rightNow = Calendar.getInstance()
    val currentHourIn24Format: Int =rightNow.get(Calendar.HOUR_OF_DAY)
    return when (forecast?.find { it.hour.toInt() ==  currentHourIn24Format }?.uv ?: 0.0) {
        in 0.0..2.0 -> SolarActivity.Low
        in 3.0..5.0 -> SolarActivity.Medium
        in 6.0..7.0 -> SolarActivity.High
        in 8.0..10.0 -> SolarActivity.VeryHigh
        else -> SolarActivity.VeryHigh
    }
}
