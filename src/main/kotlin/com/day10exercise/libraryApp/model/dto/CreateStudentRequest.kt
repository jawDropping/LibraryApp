package com.day10exercise.libraryApp.model.dto

data class CreateStudentRequest(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val role: String
)