package com.day10exercise.libraryApp.model.dto

import java.util.UUID

data class BookResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val isbn: String,
    val genre: String,
    val totalCopies: Int,
    val availableCopies: Int,
    val borrowedCopies: Int,
    val overdueCopies: Int,
    val isArchived: Boolean
)