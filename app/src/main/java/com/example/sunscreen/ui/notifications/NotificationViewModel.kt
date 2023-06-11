package com.example.sunscreen.ui.notifications

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.UpdateUserUseCase
import com.example.sunscreen.ui.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState: StateFlow<NotificationState> = _notificationState



}
