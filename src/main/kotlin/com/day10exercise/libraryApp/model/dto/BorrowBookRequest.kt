package com.day10exercise.libraryApp.model.dto

import java.util.UUID

data class BorrowBookRequest(
    val studentId: UUID,
    val bookId: UUID
)