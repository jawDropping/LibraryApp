package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.mapper.toEntity
import com.day10exercise.libraryApp.mapper.toResponse
import com.day10exercise.libraryApp.model.dto.*
import com.day10exercise.libraryApp.model.entity.Role
import com.day10exercise.libraryApp.model.entity.User
import com.day10exercise.libraryApp.repository.StudentRepository
import com.day10exercise.libraryApp.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) {

    fun getAllStudents(): List<StudentResponse> =
        studentRepository.findAll().map { it.toResponse() }

    fun getStudent(id: UUID): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        return student.toResponse()
    }
    @Transactional
    fun createStudent(request: CreateStudentRequest): StudentResponse {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = mutableSetOf(Role.STUDENT)
        )
        val savedUser = userRepository.save(user)

        val student = request.toEntity(savedUser)
        val savedStudent = studentRepository.save(student)

        // FIX: Pass savedUser directly so no extra SELECT query is fired!
        return savedStudent.toResponse(savedUser)
    }

    fun deleteStudent(id: UUID) {
        studentRepository.deleteById(id)
    }

    fun updateStudent(id: UUID, request: UpdateStudentRequest): StudentResponse {

        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }
        request.firstName?.let {student.user.firstName = it}
        request.lastName?.let {student.user.lastName = it}
        request.email?.let {student.user.email = it}
        request.isActive?.let {student.isActive = it}

        val updated = studentRepository.save(student)

        return updated.toResponse()
    }

}