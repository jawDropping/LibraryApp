package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.AuthResponse
import com.day10exercise.libraryApp.model.dto.CreateStudentRequest
import com.day10exercise.libraryApp.model.dto.LoginRequest
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.service.StudentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (private val studentService : StudentService){

    @PostMapping("/register")
    fun registerStudent(@RequestBody register: CreateStudentRequest) :String{

        return "User registered successfully"
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse{
        return studentService.login(request)
    }
}