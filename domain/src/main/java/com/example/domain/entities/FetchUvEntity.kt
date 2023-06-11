package com.example.domain.entities

sealed class FetchUvEntity {
    object Success : FetchUvEntity()
    data class Failure(val error: String) : FetchUvEntity()
    object Loading : FetchUvEntity()
}
