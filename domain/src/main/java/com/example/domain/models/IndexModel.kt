package com.example.domain.models

import java.util.UUID

data class IndexModel (
    val id: UUID,
    val value: Double?,
    val date: String?,
    val location: String?,
    val forecast: List<Hour>?
) {
    data class Hour (
        val hour: Int?,
        val uv: Double
    )
}
