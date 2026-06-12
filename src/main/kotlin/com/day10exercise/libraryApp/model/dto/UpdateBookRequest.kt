package com.day10exercise.libraryApp.model.dto

data class UpdateBookRequest(
    val title: String?,
    val author: String?,
    val genre: String?,
    val isbn: String?,
    val totalCopies: Int?,
    val isArchived: Boolean?
)