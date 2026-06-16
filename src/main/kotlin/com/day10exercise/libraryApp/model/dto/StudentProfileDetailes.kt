package com.day10exercise.libraryApp.model.dto

import java.time.OffsetDateTime
import java.util.UUID

data class StudentProfileDetails(
    val studentId: UUID,
    val isActive: Boolean,
    val createdAt: OffsetDateTime
)