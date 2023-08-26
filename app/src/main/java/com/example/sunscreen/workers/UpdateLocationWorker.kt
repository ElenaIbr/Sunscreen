package com.example.sunscreen.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecases.UpdateLocationUseCase
import com.example.domain.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateLocationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateLocationUseCase: UpdateLocationUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("dfdscdsv", "UpdateLocationWorker")
        return updateLocationUseCase.execute(Unit).let {
            when (it) {
                is Resource.Success -> Result.success()
                is Resource.Error -> Result.failure()
            }
        }
    }
}
