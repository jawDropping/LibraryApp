package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.mapper.toEntity
import com.day10exercise.libraryApp.mapper.toResponse
import com.day10exercise.libraryApp.model.dto.*
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.repository.StudentRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StudentService(private val studentRepository: StudentRepository) {

    fun getAllStudents(): List<StudentResponse> =
        studentRepository.findAll().map { it.toResponse() }

    fun getStudent(id: UUID): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        return student.toResponse()
    }

    fun createStudent(request: CreateStudentRequest): StudentResponse {
        val student = request.toEntity()
        val saved = studentRepository.save(student)

        return saved.toResponse()
    }

    fun deleteStudent(id: UUID) {
        studentRepository.deleteById(id)
    }

    fun updateStudent(id: UUID, request: UpdateStudentRequest): StudentResponse {

        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }
        request.firstName?.let {student.firstName = it}
        request.lastName?.let {student.lastName = it}
        request.email?.let {student.email = it}
        request.isActive?.let {student.isActive = it}

        val updated = studentRepository.save(student)

        return updated.toResponse()
    }

}