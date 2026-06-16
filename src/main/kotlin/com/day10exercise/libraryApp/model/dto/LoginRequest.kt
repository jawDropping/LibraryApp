package com.day10exercise.libraryApp.model.dto

import jakarta.validation.constraints.*

data class LoginRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be validly formatted")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)