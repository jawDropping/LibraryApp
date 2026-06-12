package com.day10exercise.libraryApp.model.dto

import java.time.OffsetDateTime
import java.util.UUID

data class StudentResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isActive: Boolean,
    val createdAt: OffsetDateTime,
)