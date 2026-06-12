package com.day10exercise.libraryApp.model.dto

import com.day10exercise.libraryApp.model.entity.BorrowStatus
import java.time.OffsetDateTime
import java.util.UUID

data class BorrowedBookResponse(
    val id: UUID,
    val studentId: UUID,
    val studentName: String,
    val bookId: UUID,
    val bookTitle: String,
    val borrowedAt: OffsetDateTime,
    val returnedAt: OffsetDateTime?,
    val borrowStatus: BorrowStatus
)