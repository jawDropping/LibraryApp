package com.day10exercise.libraryApp.model.dto

data class UserUpdateRequest(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val isActive: Boolean? // For updating student status
)