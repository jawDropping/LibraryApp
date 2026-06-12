package com.day10exercise.libraryApp.model.dto

data class ReturnBookRequest (
    val title: String,
    val author: String,
    val genre: String,
    val totalCopies: Int?,
    val isArchived: Boolean?,
)

