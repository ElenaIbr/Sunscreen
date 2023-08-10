package com.example.sunscreen.ui.index.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FetchUvEntity
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

    private fun getUvValue() {
        viewModelScope.launch {
            getUvValueUseCase.execute(Unit).collect { flow ->
                _indexState.value = _indexState.value.copy(
                    index = flow?.indexModel,
                    solarActivityLevel = flow?.solarActivityLevel
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
                        forecast = flow
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
    fun fetchForecast() {
        viewModelScope.launch {
            fetchForecastInBackgroundUseCase.execute("${_indexState.value.latitude},${_indexState.value.longitude}")
        }
    }
    fun fetchUvValue() {
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
    fun setCoordinates(latitude: Double, longitude: Double) {
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
