package com.example.sunscreen.ui.index.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Coordinates
import com.example.domain.models.InternetConnectivityEntity
import com.example.domain.models.SolarActivity
import com.example.domain.usecases.FetchForecastUseCase
import com.example.domain.usecases.FetchUvUseCase
import com.example.domain.usecases.GetForecastByDateUseCase
import com.example.domain.usecases.GetUserNameUseCase
import com.example.domain.usecases.GetUvValueUseCase
import com.example.domain.usecases.ObserveInternetConnectivityUseCase
import com.example.domain.usecases.UpdateLocationInBackgroundUseCase
import com.example.domain.usecases.UpdateLocationUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val fetchUvUseCase: FetchUvUseCase,
    private val getUvValueUseCase: GetUvValueUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val fetchForecastUseCase: FetchForecastUseCase,
    private val getForecastByDateUseCase: GetForecastByDateUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase,
    private val updateLocationInBackgroundUseCase: UpdateLocationInBackgroundUseCase,
    private val observeInternetConnectivityUseCase: ObserveInternetConnectivityUseCase
) : ViewModel() {

    private val _indexState = MutableStateFlow(IndexState())
    val indexState: StateFlow<IndexState> = _indexState

    init {
        getUvValue()
        getUserName()
        updateLocationInBackgroundUseCase()
    }

    fun sendEvent(indexEvent: IndexEvent) {
        when(indexEvent) {
            is ObserveInternetConnectivity -> {
                observeInternetConnectivity()
            }
            is SetCoordinates -> {
                setCoordinates(indexEvent.latitude, indexEvent.longitude)
            }
            is UpdateLocation -> {
                updateLocation()
            }
            is SetSolarActivity -> {
                setSolarActivity(indexEvent.indexValue)
            }
        }
    }

    private fun observeInternetConnectivity() {
        viewModelScope.launch {
            observeInternetConnectivityUseCase.execute(Unit).collect { flow ->
                when (flow) {
                    InternetConnectivityEntity.Available -> {
                        _indexState.value = _indexState.value.copy(
                            isInternetAvailable = true,
                        )
                        fetchData()
                    }
                    InternetConnectivityEntity.Unavailable -> {
                        _indexState.value = _indexState.value.copy(
                            isInternetAvailable = false,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchForecast()
        }
        viewModelScope.launch {
            fetchUvValue()
        }
    }

    private fun getUvValue() {
        viewModelScope.launch {
            getUvValueUseCase.execute(Unit).collect { flow ->
                flow?.indexModel?.let { indexModel ->
                    _indexState.value = _indexState.value.copy(
                        index = indexModel,
                        indexValue = indexModel.value
                    )
                }
                flow?.indexModel?.value?.let { value ->
                    _indexState.value = _indexState.value.copy(
                        solarActivityLevel = getSolarActivityLevel(value)
                    )
                }
                getForecast()
            }
        }
    }
    private fun getForecast() {
        viewModelScope.launch {
            getForecastByDateUseCase.execute(getLocalDateTime()).collect { flow ->
                _indexState.value = _indexState.value.copy(
                    forecast = flow
                )
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
            when (
                fetchForecastUseCase.execute(
                    Coordinates(
                        longitude = _indexState.value.longitude ?: 0.0,
                        latitude = _indexState.value.latitude ?: 0.0,
                        date = getLocalDateTime().toString()
                    )
                )
            ) {
                is Resource.Success -> {
                    _indexState.value = _indexState.value.copy(
                        isForecastLoading = false
                    )
                }
                is Resource.Error -> {}
            }
        }
    }
    private fun fetchUvValue() {
        viewModelScope.launch {
            if (_indexState.value.latitude != null && _indexState.value.longitude != null) {
                when(
                    fetchUvUseCase.execute(
                        input = Coordinates(
                            latitude = _indexState.value.latitude ?: 0.0,
                            longitude = _indexState.value.longitude ?: 0.0,
                            date = getLocalDateTime().toString()
                        )
                    )
                ) {
                    is Resource.Success -> {
                        _indexState.value = _indexState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {}
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
    private fun updateLocation() {
        viewModelScope.launch {
            updateLocationUseCase.execute(Unit)
        }
    }
    private fun updateLocationInBackgroundUseCase() {
        viewModelScope.launch {
            updateLocationInBackgroundUseCase.execute(Unit)
        }
    }
    private fun setSolarActivity(indexValue: Double) {
        _indexState.value = _indexState.value.copy(
            solarActivityLevel = getSolarActivityLevel(indexValue),
            indexValue = indexValue
        )
    }
}
fun getSolarActivityLevel(index: Double): SolarActivity {
    return when (index) {
        in 0.0..2.0 -> SolarActivity.Low
        in 2.0..5.0 -> SolarActivity.Medium
        in 5.0..8.0 -> SolarActivity.High
        in 8.0..10.0 -> SolarActivity.VeryHigh
        else -> SolarActivity.VeryHigh
    }
}

fun getLocalDateTime(): LocalDateTime {
    val date = Date()
    val utc: ZonedDateTime = date.toInstant().atZone(ZoneOffset.UTC)
    val default = utc.withZoneSameInstant(ZoneId.systemDefault())
    return default.toLocalDateTime()
}
