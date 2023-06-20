package com.example.sunscreen.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecases.FetchForecastUseCase
import com.example.domain.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FetchForecastWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val fetchForecastUseCase: FetchForecastUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return inputData.getString("coordinates")?.let { coordinates ->
            fetchForecastUseCase.execute(
                input = coordinates
            ).let {
                when (it) {
                    is Resource.Success -> Result.success()
                    is Resource.Error -> Result.failure()
                }
            }
        } ?: Result.failure()
    }
}
