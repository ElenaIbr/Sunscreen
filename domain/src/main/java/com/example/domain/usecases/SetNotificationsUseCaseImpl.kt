package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.Notification
import com.example.domain.services.NotificationsManager
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SetNotificationsUseCaseImpl @Inject constructor(
    private val notificationsManager: NotificationsManager
) : SetNotificationsUseCase,
    SingleUseCase<Notification, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: Notification): Resource<Unit> {
        if (input.notificationEnabled) {
            notificationsManager.startNotifications(input.start)
        } else notificationsManager.stopNotifications()
        return Resource.Success(Unit)
    }

}
