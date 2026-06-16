package com.day10exercise.libraryApp.controller

import com.day10exercise.libraryApp.model.dto.*
import com.day10exercise.libraryApp.service.StudentService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/students")
class StudentController(
    private val studentService: StudentService
) {

    @GetMapping
    fun getAllStudents() = studentService.getAllStudents()

    @GetMapping("/{id}")
    fun getStudent(@PathVariable id: UUID) = studentService.getStudent(id)

    @PostMapping
    fun createStudent(@RequestBody request: CreateStudentRequest) = studentService.createStudent(request)

    @DeleteMapping("/{id}")
    fun deleteStudent(@PathVariable id: UUID) {
        studentService.deleteStudent(id)
    }

    @PatchMapping("/{id}")
    fun updateStudent(@PathVariable id: UUID, @RequestBody request: UpdateStudentRequest): StudentResponse {
        return studentService.updateStudent(id, request)
    }
}