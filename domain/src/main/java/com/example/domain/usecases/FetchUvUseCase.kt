package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.Coordinates
import com.example.domain.utils.Resource

interface FetchUvUseCase : SingleUseCaseInterface<Coordinates, Resource<Unit>>
