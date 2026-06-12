package com.day10exercise.libraryApp.model.dto

data class CreateBookRequest(
    val title: String,
    val author: String,
    val isbn: String,
    val genre: String,
    val totalCopies: Int
)