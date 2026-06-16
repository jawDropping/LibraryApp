package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.AuthResponse
import com.day10exercise.libraryApp.model.dto.CreateStudentRequest
import com.day10exercise.libraryApp.model.dto.LoginRequest
import com.day10exercise.libraryApp.model.dto.RegisterRequest
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.service.AuthService
import com.day10exercise.libraryApp.service.StudentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val response = authService.login(request)
        return ResponseEntity.ok(response)
    }
}