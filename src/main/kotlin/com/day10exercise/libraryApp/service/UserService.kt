package com.day10exercise.libraryApp.service

import com.day10exercise.libraryApp.mapper.toResponse
import com.day10exercise.libraryApp.model.dto.UpdateAccountRequest
import com.day10exercise.libraryApp.model.dto.UserResponse
import com.day10exercise.libraryApp.model.dto.UserUpdateRequest
import com.day10exercise.libraryApp.model.entity.Role
import com.day10exercise.libraryApp.model.entity.Student
import com.day10exercise.libraryApp.model.entity.User
import com.day10exercise.libraryApp.repository.StudentRepository
import com.day10exercise.libraryApp.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository, private val studentRepository: StudentRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun getAllUsers(): List<UserResponse> {
        val users = userRepository.findAll()
        // Fetch students to map profiles efficiently in memory
        val studentsMap = studentRepository.findAll().associateBy { it.user.id }

        return users.map { user ->
            user.toResponse(studentsMap[user.id])
        }
    }

    @Transactional
    fun updateUser(id: UUID, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        // 1. Update Core Identity Fields
        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.email?.let { user.email = it }
        val updatedUser = userRepository.save(user)

        // 2. Conditionally update Role Profiles (e.g., Student Status)
        var studentProfile: Student? = null
        if (user.roles.contains(Role.STUDENT)) {
            val student = studentRepository.findByUser_Id(id)
                .orElseThrow { RuntimeException("Student profile missing for user context") }

            request.isActive?.let { student.isActive = it }
            studentProfile = studentRepository.save(student)
        }

        return updatedUser.toResponse(studentProfile)
    }

    @Transactional
    fun deleteUser(id: UUID) {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        // Clean up role profile relationships manually if cascading isn't set up at the DB level
        studentRepository.findByUser_Id(id).ifPresent { student ->
            studentRepository.delete(student)
        }

        userRepository.delete(user)
    }

    @Transactional
    fun updateAccountCredentials(id: UUID, request: UpdateAccountRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        request.email?.let {
            if (userRepository.existsByEmail(it) && user.email != it) {
                throw RuntimeException("Email is already taken")
            }
            user.email = it
        }

        request.password?.let {
            user.password = passwordEncoder.encode(it)
        }

        val updatedUser = userRepository.save(user)
        val studentProfile = studentRepository.findByUser_Id(id).orElse(null)

        return updatedUser.toResponse(studentProfile)
    }

    fun getUserById(id: UUID): UserResponse {
        val user = userRepository.findById(id).orElseThrow { RuntimeException("User not found") }
        val student = studentRepository.findByUser_Id(id).orElse(null)
        return user.toResponse()
    }
}