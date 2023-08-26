package com.example.sunscreen.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.models.Coordinates
import com.example.domain.usecases.FetchForecastUseCase
import com.example.domain.usecases.UpdateLocationUseCase
import com.example.domain.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FetchForecastWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val fetchForecastUseCase: FetchForecastUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        updateLocationUseCase.execute(Unit).let { result ->
            when (result) {
                is Resource.Success -> {}
                is Resource.Error -> Result.retry()
            }
        }
        val latitude = inputData.getDouble("latitude", 0.0)
        val longitude = inputData.getDouble("longitude", 0.0)
        val date = inputData.getString("date")

        return if (latitude != 0.0 && longitude != 0.0 && !date.isNullOrEmpty()) {
            fetchForecastUseCase.execute(
                input = Coordinates(
                    latitude = latitude,
                    longitude = longitude,
                    date = date
                )
            ).let {
                when (it) {
                    is Resource.Success -> Result.success()
                    is Resource.Error -> Result.failure()
                }
            }
            Result.success()
        } else Result.failure()
    }
}
