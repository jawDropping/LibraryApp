package com.day10exercise.libraryApp.model.dto

import jakarta.validation.constraints.*

data class UpdateAccountRequest(
    @field:Email(message = "Email must be validly formatted")
    val email: String?,

    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
        message = "Password must be alphanumeric, containing at least one uppercase letter, one lowercase letter, and one number"
    )
    val password: String?
)