package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.SolarActivity
import com.example.domain.utils.Resource

interface FetchForecastForNotification : SingleUseCaseInterface<String, Resource<SolarActivity>>
