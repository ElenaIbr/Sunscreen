package com.example.sunscreen.ui.notifications

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    
}
