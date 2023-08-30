package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.Notification
import com.example.domain.utils.Resource

interface SetNotificationsUseCase : SingleUseCaseInterface<Notification, Resource<Unit>>
