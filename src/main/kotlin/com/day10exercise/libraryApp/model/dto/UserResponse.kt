package com.day10exercise.libraryApp.model.dto

import com.day10exercise.libraryApp.model.entity.Role
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: Set<Role>,
    val studentProfile: StudentProfileDetails? = null // Extensible for TeacherProfileDetails later!
)