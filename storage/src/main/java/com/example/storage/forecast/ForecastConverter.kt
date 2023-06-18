package com.example.storage.forecast

import com.example.domain.models.ForecastModel
import com.example.storage.StorageConverter
import java.util.UUID
import javax.inject.Inject

class ForecastConverter @Inject constructor(): StorageConverter<ForecastStorageModel, ForecastModel> {
    override fun convertToStorage(domainModel: ForecastModel): ForecastStorageModel {
        return with(domainModel) {
            ForecastStorageModel(
                id = UUID.randomUUID(),
                date = date,
                forecast = forecast
            )
        }
    }
    override fun convertFromStorage(storageModel: ForecastStorageModel): ForecastModel {
        return with(storageModel) {
            ForecastModel(
                date = date,
                forecast = forecast
            )
        }
    }
}
