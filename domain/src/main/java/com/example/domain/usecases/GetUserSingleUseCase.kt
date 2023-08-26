package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.UserModel
import com.example.domain.utils.Resource

interface GetUserSingleUseCase : SingleUseCaseInterface<Unit, Resource<UserModel>>
