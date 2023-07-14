package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.utils.Resource

interface UpdateLocationUseCase : SingleUseCaseInterface<Unit, Resource<Unit>>
