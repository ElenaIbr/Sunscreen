package com.example.domain.entities

import com.example.domain.models.UvValueModel

sealed class UvValueEntity {
    data class Success(val uvValueModel: UvValueModel) : UvValueEntity()
    data class Failure(val message: String) : UvValueEntity()
    object Loading : UvValueEntity()
}
