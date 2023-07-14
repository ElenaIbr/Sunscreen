package com.example.sunscreen.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FetchUvEntity
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.models.UvValueModel
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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchUvUseCase: FetchUvUseCase,
    private val getUvValueUseCase: GetUvValueUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getDateAndDayOfWeekUseCase: GetDateAndDayOfWeekUseCase,
    private val fetchForecastInBackgroundUseCase: FetchForecastInBackgroundUseCase,
    private val getLocationInBackgroundUseCase: GetLocationInBackgroundUseCase,
    private val getForecastByDateUseCase: GetForecastByDateUseCase
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        getLocation()
        getUvValue()
        getUserName()
        getDateAndDayOfWeekUseCase()
    }

    private fun getUvValue() {
        viewModelScope.launch {
            getUvValueUseCase.execute(Unit).collect { flow ->
                _mainState.value = _mainState.value.copy(
                    index = flow?.indexModel,
                    solarActivityLevel = flow?.solarActivityLevel
                )
                getForecast()
            }
        }
    }

    fun fetchForecast() {
        viewModelScope.launch {
            fetchForecastInBackgroundUseCase.execute("${_mainState.value.latitude},${_mainState.value.longitude}")
        }
    }

    fun getForecast() {
        _mainState.value.index?.date?.let { date ->
            viewModelScope.launch {
                getForecastByDateUseCase.execute(date).collect { flow ->
                    Log.d("dsfsdfds", flow.toString())
                    _mainState.value = _mainState.value.copy(
                        forecast = flow
                    )
                }
            }
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            getUserNameUseCase.execute(Unit).collect { flow ->
                _mainState.value = _mainState.value.copy(
                    userName = flow
                )
            }
        }
    }

    fun fetchUvValue() {
        viewModelScope.launch {
            fetchUvUseCase.execute(
                "${_mainState.value.latitude},${_mainState.value.longitude}"
            ).collect { flow ->
                when (flow) {
                    is FetchUvEntity.Success -> {
                        _mainState.value = _mainState.value.copy(
                            isLoading = false
                        )
                    }
                    is FetchUvEntity.Loading -> {
                        _mainState.value = _mainState.value.copy(
                            isLoading = true
                        )
                    }
                    is FetchUvEntity.Failure -> {
                        _mainState.value = _mainState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun setCoordinates(latitude: Double, longitude: Double) {
        if (
            _mainState.value.latitude?.toInt() != latitude.toInt() ||
            _mainState.value.longitude?.toInt() != longitude.toInt()
        ) {
            _mainState.value = _mainState.value.copy(
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    fun getLocation() {
        viewModelScope.launch {
            getLocationInBackgroundUseCase.execute(Unit)
        }
    }

    private fun getDateAndDayOfWeekUseCase() {
        viewModelScope.launch {
            getDateAndDayOfWeekUseCase.execute(Unit).collect { flow ->
                _mainState.value = _mainState.value.copy(
                    date = flow
                )
            }
        }
    }
}


data class MainState(
    val userName: String? = null,
    val weather: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val index: IndexModel? = null,
    val date: String? = null,
    val temperature: String? = null,
    val forecast: ForecastModel? = null,
    val solarActivityLevel: UvValueModel.SolarActivityLevel? = null,
    val isLoading: Boolean = true
)
