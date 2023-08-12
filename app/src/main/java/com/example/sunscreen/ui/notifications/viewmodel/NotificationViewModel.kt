package com.example.sunscreen.ui.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Notification
import com.example.domain.usecases.GetUserEntity
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.UpdateNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState: StateFlow<NotificationState> = _notificationState

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.execute(Unit).collect { flow ->
                when (flow) {
                    is GetUserEntity.Success -> {
                        _notificationState.value = _notificationState.value.copy(
                            user = flow.userModel,
                            notificationWasChanged = false
                        )
                        flow.userModel?.notifications?.let { notification ->
                            _notificationState.value = _notificationState.value.copy(
                                notification = notification
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
    fun setNotifications(notification: Notification) {
        _notificationState.value = _notificationState.value.copy(
            notification = notification
        )
        userDataWasChanged()
    }
    fun updateNotifications() {
        _notificationState.value.notification?.let { notification ->
            viewModelScope.launch {
                updateNotificationsUseCase.execute(notification)
            }
        }
    }
    private fun userDataWasChanged() {
        _notificationState.value = _notificationState.value.copy(
            notificationWasChanged = _notificationState.value.notification?.notificationEnabled != _notificationState.value.user?.notifications?.notificationEnabled
                    || _notificationState.value.notification?.start != _notificationState.value.user?.notifications?.start
        )
    }
}
