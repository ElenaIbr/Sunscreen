package com.example.sunscreen.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FetchUvEntity
import com.example.domain.models.IndexModel
import com.example.domain.models.UvValueModel
import com.example.domain.usecases.FetchUvUseCase
import com.example.domain.usecases.GetDateAndDayOfWeekUseCase
import com.example.domain.usecases.GetUserNameUseCase
import com.example.domain.usecases.GetUvValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchUvUseCaseImpl: FetchUvUseCase,
    private val getUvValueUseCase: GetUvValueUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getDateAndDayOfWeekUseCase: GetDateAndDayOfWeekUseCase
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
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
            fetchUvUseCaseImpl.execute(
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
    val solarActivityLevel: UvValueModel.SolarActivityLevel? = null,
    val isLoading: Boolean = true
)
