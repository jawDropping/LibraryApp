package com.day10exercise.libraryApp.model.dto

data class UpdateStudentRequest(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val isActive: Boolean?
)